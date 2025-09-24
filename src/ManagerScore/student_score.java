package ManagerScore;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Database.DAO;
import Database.StudentRecordDTO;
import Server.Client;
import manager.manager_main;

public class student_score extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable table;
    StudentRecordDTO record = new StudentRecordDTO();
    DAO db = new DAO();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					student_score frame = new student_score();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public student_score() throws IOException {

    	Client client = new Client();
		setTitle("점수 관리");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 466, 287);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("등록");
		btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		student_score_insert manage = new student_score_insert();
	    		manage.setVisible(true);
	    		dispose();
	    	}
		});
		btnNewButton_1.setBounds(259, 19, 96, 69);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("뒤로가기");
		btnNewButton_2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		manager_main manage = new manager_main();
	    		manage.setVisible(true);
	    		dispose();
	    	}
		});
		btnNewButton_2.setBounds(360, 19, 96, 69);
		panel.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("찾기를 누르면 등록된");
		lblNewLabel.setBounds(22, 26, 150, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("모든 학생을 조회합니다.");
		lblNewLabel_2.setBounds(22, 41, 150, 15);
		panel.add(lblNewLabel_2);
		 
		JLabel lblNewLabel_1 = new JLabel("* 점수 등록된 학생만 조회됩니다.");
		panel.add(lblNewLabel_1);
		Font font = new Font(lblNewLabel_1.getFont().getName(), Font.PLAIN, 8); // 여기서 12는 새로운 글꼴 크기입니다.

		// Font 객체를 라벨에 설정합니다.
		lblNewLabel_1.setFont(font);

		lblNewLabel_1.setBounds(22, 70, 150, 15);
		panel.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
	                 
	               },
	               new String[] {
	                  "학번", "이름","사유", "점수"
	               }   
	            ));
	      scrollPane.setViewportView(table);
	      table.getColumn("학번").setPreferredWidth(30);
	      table.getColumn("이름").setPreferredWidth(30);
	      table.getColumn("점수").setPreferredWidth(30);
	      table.getColumn("사유").setPreferredWidth(60);

	      scrollPane.setViewportView(table);
	      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //테이블 밑에 사용 튜플값들 가운데 정렬
	      dtcr.setHorizontalAlignment(SwingConstants.CENTER);
	      TableColumnModel tcm = table.getColumnModel();
	      for(int i = 0; i < tcm.getColumnCount(); i++) {
	         tcm.getColumn(i).setCellRenderer(dtcr);
	      }
	      scrollPane.setBounds(12, 100, 445, 183);
	      table.setBounds(12, 100, 445, 183);
	      panel.add(scrollPane);
		
		JButton btnNewButton = new JButton("찾기");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	record.setUniversityNumber("");
		    	record.setName("");
		    	List<StudentRecordDTO> studentList = null;
				try {
					studentList = client.ScoreBring(record);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    	// Clear existing rows from the table
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                // Add rows from the studentList to the table
                for (StudentRecordDTO student : studentList) {
                    Object[] rowData = {
                            student.getUniversityNumber(),
                            student.getName(),
                            student.getReason(),
                            student.getScore()
                    };
                    model.addRow(rowData);
                }
                table.addMouseListener(new java.awt.event.MouseAdapter() {
	                @Override
	                public void mouseClicked(java.awt.event.MouseEvent evt) {
	                    int row = table.rowAtPoint(evt.getPoint());
	                    int col = table.columnAtPoint(evt.getPoint());
	                    if (row >= 0 && col >= 0) {
	                        // 선택한 행의 데이터 가져오기
	                        String universityNumber = (String) table.getValueAt(row, 0);
	                        String name = (String) table.getValueAt(row, 1);
	                        String Reason = (String) table.getValueAt(row, 2);
	                        String Score = (String) table.getValueAt(row, 3);

	                        // 새로운 UI 생성 및 데이터 전달
	                        student_score_repair repair = new student_score_repair(universityNumber, name, Reason, Score);
	                        repair.setVisible(true);
	                    }
	                }
	            });
		    }
		});
		btnNewButton.setBounds(159, 19, 96, 69);
		panel.add(btnNewButton);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(10, 98, 448, 187);
		panel.add(progressBar_1);
		
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 10, 448, 86);
		panel.add(progressBar);
		setLocationRelativeTo(null);
	}
}
                
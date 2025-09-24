package ManagerScore;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Database.DAO;
import Database.StudentRecordDTO;
import Server.Client;

public class student_score_insert extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	StudentRecordDTO record = new StudentRecordDTO();
	DAO db = new DAO();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					student_score_insert frame = new student_score_insert();
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
	public student_score_insert() throws IOException {
    	Client client = new Client();
		setTitle("점수 등록");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 471, 415);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(77, 359, 263, 33);
		panel.add(textField_6);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(230, 331, 110, 21);
		panel.add(textField_5);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(77, 331, 110, 21);
		panel.add(textField_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(77, 303, 110, 21);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButtoncheck = new JButton("학번확인");
		btnNewButtoncheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numcheck = db.record_student_check(textField_3.getText());
				if(!numcheck.equals("")) {
					showConfirmationDialog("존재하는 학번입니다!");
					textField_3.setEditable(false);
					textField_4.setEditable(false);
					textField_4.setText(numcheck);
				}
				else {
					showConfirmationDialog("존재하지 않는 학번입니다!");
					textField_3.setText("");
				}
			}
		});
		btnNewButtoncheck.setBounds(197, 301, 140, 21);
		panel.add(btnNewButtoncheck);
		
		JButton btnNewButton_1_2_1 = new JButton("등록");
		btnNewButton_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				record.setUniversityNumber(textField_3.getText());
				record.setName(textField_4.getText());
				record.setReason(textField_6.getText());
				record.setScore(textField_5.getText());
				
				System.out.println(record.getUniversityNumber());
				
				String E = null;
				try {
					E = client.CheckInsert(record);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(E.equals("성공")) {
					showConfirmationDialog("등록되었습니다.");
				}
				else {
					showConfirmationDialog("실패되었습니다.");
				}
				student_score_insert manage = null;
				try {
					manage = new student_score_insert();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				manage.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1_2_1.setBounds(350, 295, 96, 98);
		panel.add(btnNewButton_1_2_1);
		
		JLabel lblNewLabel_3_3 = new JLabel("이름:");
		lblNewLabel_3_3.setBounds(41, 334, 50, 15);
		panel.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_3_2 = new JLabel("사유:");
		lblNewLabel_3_2.setBounds(41, 362, 50, 15);
		panel.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_1 = new JLabel("점수:");
		lblNewLabel_3_1.setBounds(197, 334, 50, 15);
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3 = new JLabel("학번:");
		lblNewLabel_3.setBounds(41, 306, 50, 15);
		panel.add(lblNewLabel_3);
		
		JProgressBar progressBar_2 = new JProgressBar();
		progressBar_2.setBounds(10, 287, 448, 118);
		panel.add(progressBar_2);
		
		JButton btnNewButton_2 = new JButton("뒤로가기");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				student_score manage = null;
				try {
					manage = new student_score();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				manage.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setBounds(350, 19, 96, 69);
		panel.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setBounds(41, 26, 50, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("학번");
		lblNewLabel_1.setBounds(41, 57, 50, 15);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(77, 23, 157, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(77, 54, 157, 21);
		textField_1.setColumns(10);
		panel.add(textField_1);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
	                 
	               },
	               new String[] {
	                  "학번", "이름", "점수", "사유"
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
       
      DefaultTableModel model = (DefaultTableModel)table.getModel();

      JButton btnNewButton = new JButton("찾기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				record.setName(textField.getText());
				record.setUniversityNumber(textField_1.getText());
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
	                        String reason = (String) table.getValueAt(row, 2);
	                        String score = (String) table.getValueAt(row, 3);

	                        // 새로운 UI 생성 및 데이터 전달
	                        student_score_repair repair = new student_score_repair(universityNumber, name,reason,score);
	                        repair.setVisible(true);
	                    }
	                }
	            });
			}
		});
		btnNewButton.setBounds(244, 19, 96, 69);
		panel.add(btnNewButton);
			
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(10, 98, 448, 187);
		panel.add(progressBar_1);
		
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 10, 448, 86);
		panel.add(progressBar);
		setLocationRelativeTo(null);
	}
	private void showConfirmationDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
}
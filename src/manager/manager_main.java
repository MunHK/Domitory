package manager;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Database.DAO;
import Database.SrudentInfoRoomDTO;
import ManagerInandOut.inandout_manage;
import ManagerOutside.outside_manage;
import ManagerRoom.room_number;
import ManagerScore.student_score;
import UserInformation.student_info_main;

public class manager_main extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	SrudentInfoRoomDTO inforoom = new SrudentInfoRoomDTO();
	DAO db = new DAO();
	SrudentInfoRoomDTO student1 = new SrudentInfoRoomDTO();
	
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public manager_main() {
		setTitle("관리자 메인화면");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 488, 321);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("학생관리");
		btnNewButton_2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		student_info_main manage = new student_info_main();
	    		manage.setVisible(true);
	    		dispose();
	    	}
		});
		btnNewButton_2.setBounds(152, 54, 87, 34);
		panel.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setBounds(20, 22, 50, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("학번");
		lblNewLabel_1.setBounds(20, 47, 50, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("호실");
		lblNewLabel_2.setBounds(20, 72, 50, 15);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(55, 19, 96, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(55, 44, 96, 21);
		textField_1.setColumns(10);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(55, 69, 96, 21);
		textField_2.setColumns(10);
		panel.add(textField_2);
		
		table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "학번", "이름", "성별", "학과", "전화번호", "호실" }
                )
        );
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(12, 100, 465, 210);
        panel.add(scrollPane);
        
		JButton btnNewButton = new JButton("찾기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inforoom.setName(textField.getText());
				inforoom.setUniversityNumber(textField_1.getText());
				inforoom.setRoom(textField_2.getText());
				List<SrudentInfoRoomDTO> studentList = db.Search_Student_room(inforoom);

                // Clear existing rows from the table
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                // Add rows from the studentList to the table
                for (SrudentInfoRoomDTO student : studentList) {
                    Object[] rowData = {
                            student.getUniversityNumber(),
                            student.getName(),
                            student.getSex(),
                            student.getDepartment(),
                            student.getPhoneNumber(),
                            student.getRoom()
                    };
                    student1.setUniversityNumber(student.getUniversityNumber());
                    student1.setName(student.getName());
                    student1.setSex(student.getSex());
                    student1.setDepartment(student.getDepartment());
                    student1.setPhoneNumber(student.getPhoneNumber());
                    student1.setRoom(student.getRoom());
                    model.addRow(rowData);
                }
                
                
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
                
			}
        });
		btnNewButton.setBounds(152, 18, 87, 34);
		panel.add(btnNewButton);
		
		
		JButton btnNewButton_1 = new JButton("생활관 관리");
		btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		room_number manage = new room_number();
	    		manage.setVisible(true);
	    		dispose();
	    	}
		});
		btnNewButton_1.setBounds(364, 18, 103, 34);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("점수 관리");
		btnNewButton_1_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		student_score manage = new student_score();
	    		manage.setVisible(true);
	    		dispose();
	    	}
		});
		btnNewButton_1_1.setBounds(364, 54, 103, 34);
		panel.add(btnNewButton_1_1);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(10, 98, 468, 213);
		panel.add(progressBar_1);
		
		JButton btnNewButton_1_2 = new JButton("입실/퇴실 관리");
		btnNewButton_1_2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		inandout_manage manage = new inandout_manage();
	    		manage.setVisible(true);
	    		dispose();
	    	}
		});
		btnNewButton_1_2.setBounds(241, 18, 120, 34);
		panel.add(btnNewButton_1_2);
		
		JButton btnNewButton_1_1_1 = new JButton("외박 관리");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		outside_manage manage = new outside_manage();
	    		manage.setVisible(true);
	    		dispose();
	    	}
		});
		btnNewButton_1_1_1.setBounds(243, 54, 120, 34);
		panel.add(btnNewButton_1_1_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 10, 468, 86);
		panel.add(progressBar);
		setLocationRelativeTo(null);
	}
}
package ManagerScore;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import Database.DAO;
import Database.StudentRecordDTO;
import Server.Client;

public class student_score_repair{

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	StudentRecordDTO record = new StudentRecordDTO();
	DAO db = new DAO();

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public student_score_repair(String universityNumber,String name,String reason,String score) throws IOException {
		initialize(universityNumber, name,reason,score);
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize(String universityNumber,String name,String reason,String score) throws IOException {
    	Client client = new Client();
		frame = new JFrame("학생 등록");
		frame.setTitle("점수 수정");
		setVisible(true);
		frame.setSize(new Dimension(400, 520));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 360, 385);
		frame.getContentPane().add(panel);
		
		JButton btnNewButton_1 = new JButton("취소");
		btnNewButton_1.setBounds(267, 405, 105, 70);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("학번: ");
		lblNewLabel.setBounds(85, 57, 57, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("이름: ");
		lblNewLabel_1.setBounds(85, 141, 57, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("사유:");
		lblNewLabel_2.setBounds(85, 215, 57, 15);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("점수:");
		lblNewLabel_3.setBounds(85, 283, 57, 15);
		panel.add(lblNewLabel_3);
		
		textField = new JTextField(universityNumber);
		textField.setEditable(false);
		textField.setBounds(158, 54, 116, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(name);
		textField_1.setEditable(false);
		textField_1.setBounds(158, 138, 116, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField(reason);
		textField_2.setBounds(158, 212, 116, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField(score);
		textField_3.setBounds(158, 280, 116, 21);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		
		
		JButton btnNewButton = new JButton("수정");
		btnNewButton.setBounds(12, 405, 117, 70);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				record.setUniversityNumber(textField.getText());
				record.setName(textField_1.getText());
				record.setReason(textField_2.getText());
				record.setScore(textField_3.getText());
				String result = null;
				try {
					result = client.CheckUpdate(record,reason);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(result.equals("성공")) {
					showConfirmationDialog("수정되었습니다!");
				}
				else {
					showConfirmationDialog("수정 실패");
				}
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnNewButton_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(0, 0, 360, 385);
		panel.add(progressBar);
		
		JButton btnNewButton_2 = new JButton("삭제");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				record.setUniversityNumber(textField.getText());
				record.setName(textField_1.getText());
				record.setReason(textField_2.getText());
				record.setScore(textField_3.getText());
				boolean result = false;
				try {
					result = client.CheckDelete(record);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(result) {
					showConfirmationDialog("삭제되었습니다!");
				}
				else {
					showConfirmationDialog("삭제 실패!");
				}
				frame.dispose();
			}
		});
		btnNewButton_2.setBounds(139, 405, 118, 70);
		frame.getContentPane().add(btnNewButton_2);
		setLocationRelativeTo(null);
	}
	private void showConfirmationDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "알림", JOptionPane.INFORMATION_MESSAGE);
	}

	private void setLocationRelativeTo(Object object) {
		// TODO Auto-generated method stub
		
	}

	void setVisible(boolean b) {
	}
}

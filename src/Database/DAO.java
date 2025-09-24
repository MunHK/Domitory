package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DAO {
   Connection con;
   Statement st;
   String url = "jdbc:mysql://localhost:3306/domitory";
   String user = "root";
   String passwd = "1234";
   
   public DAO(){  //db 연동 여부
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         con = DriverManager.getConnection(url,user,passwd);
         st=con.createStatement();
         System.out.println("MySQL 서버 연동 성공");
      }catch(Exception e) {
         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
      }
   }
 //학생 점수 정보 조회
   public List<StudentRecordDTO> Search_Student_Score(StudentRecordDTO record){
	   List<StudentRecordDTO> studentList = new ArrayList<>();
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	       
	       String sql = "";
	       
	       if (!record.getName().equals("") && !record.getUniversityNumber().equals("")) { // 둘다 입력
	    	    sql = "select * from student_record where name ='" + record.getName() + "' and university_number='" + record.getUniversityNumber() + "'";
	    	    System.out.println("1");
	    	} 
	       else if (record.getName().equals("") && !record.getUniversityNumber().equals("")) { // 학번만입력
	    	    sql = "select * from student_record where university_number='" + record.getUniversityNumber() + "'";
	    	    System.out.println("2");
	    	} 
	       else if ((!record.getName().equals("")) &&( record.getUniversityNumber().equals(""))) { // 이름만입력
	    	    sql = "select * from student_record where name ='" + record.getName() + "'";
	    	    System.out.println("3");
	    	} 
	       else { // 아무것도 입력 안함
	    	    sql = "select * from student_record where name !=''";
	    	    System.out.println("4");
	    	    System.out.println(record.getUniversityNumber()+record.getName());
	    	}
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       try { 
	    	   ResultSet resultSet = st.executeQuery(sql);
		       	
		            while (resultSet.next()) {
		            	StudentRecordDTO studentInfo = new StudentRecordDTO();

		                 // Retrieve values from the ResultSet and set them in the DTO
		            	studentInfo.setUniversityNumber(resultSet.getString("university_number"));
		                studentInfo.setName(resultSet.getString("name"));
		                studentInfo.setReason(resultSet.getString("reason"));
		                studentInfo.setScore(resultSet.getString("score"));
		                 // Add the DTO to the list
		                studentList.add(studentInfo);

		                 // Now you can use the studentInfo object as needed
		                System.out.println("University Number: " + studentInfo.getUniversityNumber());
		                System.out.println("Name: " + studentInfo.getName());
		                System.out.println("reason: " + studentInfo.getReason());
		                System.out.println("score: " + studentInfo.getScore()); 

		            }		            
		       }catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   return studentList; 
   }
   //로그인
   public boolean login(StudentInformationDTO information) {//로그인 확인
	      boolean flag = false;
	      
	      try {
	         String sql = "SELECT * FROM student_information WHERE university_number = ? AND birth = ?";
	         PreparedStatement preparedStatement = con.prepareStatement(sql);
	         
			 preparedStatement.setString(1, information.getUniversityNumber());
	         preparedStatement.setString(2, information.getBirth());         
			 ResultSet result = preparedStatement.executeQuery();

	            if(result.next()) {
	               flag = true;
	               System.out.println("로그인 성공");
	            }
	            
	            else {
	               flag = false;
	               System.out.println("로그인 실패");
	            }
	      } catch(Exception e) {
	         flag = false;
	         System.out.println("로그인 실패 > "+e.toString());
	      }
	      return flag;
	   }
   //외박 수정에서 정보 확인
   public List<StudentOutsideDTO> Student_update_search(String user_id) {
	   
	   List<StudentOutsideDTO> studentList = new ArrayList<>();
	   
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	
	       String sql = "SELECT * FROM student_outside WHERE university_number = ?";
	
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       
	       try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	    	   pstmt.setString(1, user_id);
	    	   ResultSet resultSet = pstmt.executeQuery();
	    	   while (resultSet.next()) {
	    		   StudentOutsideDTO studentInfo = new StudentOutsideDTO();

	                 // Retrieve values from the ResultSet and set them in the DTO
	                 studentInfo.setUniversityNumber(resultSet.getString("university_number"));
	                 studentInfo.setReason(resultSet.getString("reason"));
	                 studentInfo.setContent(resultSet.getString("content"));
	                 studentInfo.setOutingDate(resultSet.getString("outing_date"));
	                 studentInfo.setReturningDate(resultSet.getString("returning_date"));
	                 studentInfo.setSuccess(resultSet.getString("success"));
	                 
	                 // Add the DTO to the list
	                 studentList.add(studentInfo);

	                 // Now you can use the studentInfo object as needed
	                 System.out.println("University Number: " + studentInfo.getUniversityNumber());
	                 System.out.println("Name: " + studentInfo.getReason());
	                 System.out.println("OutingDate: " + studentInfo.getOutingDate());
	                 System.out.println("ReturningDate: " + studentInfo.getReturningDate());
	                 System.out.println("Success: " + studentInfo.getSuccess());
	            }
	       } catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	       
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   
	   return studentList;
   }
 //점수 정보 삭제
   public boolean student_Record_delete(StudentRecordDTO record) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "delete from student_record where university_number=? and name=? and score = ? and reason = ?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(2, record.getName());
	            pstmt.setString(4, record.getReason());
	            pstmt.setString(3, record.getScore());
	            pstmt.setString(1, record.getUniversityNumber());
	            
	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삭제되었습니다.");
	                return true;
	            } else {
	                System.out.println("데이터 점수 삭제");
	                return false;
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	return false;
	   
   }
   //학생 정보, 방정보 전체조회
   public List<SrudentInfoRoomDTO> Search_Student_room(SrudentInfoRoomDTO inforoom){
	   List<SrudentInfoRoomDTO> studentList = new ArrayList<>();
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	       
	       String sql = "";
	       
	       if ((!inforoom.getName().equals("")) && (!inforoom.getUniversityNumber().equals(""))&& (!inforoom.getRoom().equals(""))) { // 셋다
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number and student_information.name ='" + inforoom.getName() 
	    	    + "' and student_room.university_number='" + inforoom.getUniversityNumber() + "' and student_room.room='" + inforoom.getRoom() + "'";
	    	} 
	    	else if (!inforoom.getName().equals("") && !inforoom.getUniversityNumber().equals("")&& inforoom.getRoom().equals("")) { // 이름 학번
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number and student_information.name ='" + inforoom.getName() 
	    	    + "' and student_room.university_number='" + inforoom.getUniversityNumber() + "'";
	    	} 
	    	else if (!inforoom.getName().equals("") && inforoom.getUniversityNumber().equals("") && !inforoom.getRoom().equals("")) { // 이름 호수
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number and student_information.name ='" + inforoom.getName() 
	    	    + "' and student_room.room='" + inforoom.getRoom() + "'";
	    	} 
	    	else if (inforoom.getName().equals("") && !inforoom.getUniversityNumber().equals("")&& !inforoom.getRoom().equals("")) { // 학번 호수
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number and student_room.university_number='" + inforoom.getUniversityNumber()
	    	    + "' and student_room.room='" + inforoom.getRoom() + "'";
	    	} 
	    	else if (inforoom.getName().equals("") && !inforoom.getUniversityNumber().equals("")&& inforoom.getRoom().equals("")) { // 학번만입력
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number and student_room.university_number='" + inforoom.getUniversityNumber() + "'";
	    	} 
	    	else if (!inforoom.getName().equals("") && inforoom.getUniversityNumber().equals("")&& inforoom.getRoom().equals("")) { // 이름만입력
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number and student_information.name ='" + inforoom.getName() 
	    	    + "'";
	    	}
	    	else if (inforoom.getName().equals("") && inforoom.getUniversityNumber().equals("")&& !inforoom.getRoom().equals("")) { // 호수만입력
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number and student_room.room='" + inforoom.getRoom() + "'";
	    	} 
	    	else { // 아무것도 입력 안함
	    	    sql = "select * from student_information, student_room where student_information.university_number = student_room.university_number";
	    	}
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       try { 
	    	   ResultSet resultSet = st.executeQuery(sql);
		       	
		            while (resultSet.next()) {
		            	SrudentInfoRoomDTO studentInfo = new SrudentInfoRoomDTO();

		                 // Retrieve values from the ResultSet and set them in the DTO
		            	studentInfo.setUniversityNumber(resultSet.getString("student_information.university_number"));
		                studentInfo.setName(resultSet.getString("student_information.name"));
		                studentInfo.setSex(resultSet.getString("student_information.Sex"));
		                studentInfo.setDepartment(resultSet.getString("student_information.department"));
		                studentInfo.setRoom(resultSet.getString("student_room.room"));
		                studentInfo.setPhoneNumber(resultSet.getString("student_information.phone_number"));
		                studentInfo.setAddress(resultSet.getString("student_information.address"));
		                studentInfo.setBirth(resultSet.getString("student_information.birth"));
		                studentInfo.setStartDate(resultSet.getString("student_room.start_date"));
		                studentInfo.setEndDate(resultSet.getString("student_room.end_date"));
		                
		                 // Add the DTO to the list
		                studentList.add(studentInfo);

		                 // Now you can use the studentInfo object as needed
		                System.out.println("University Number: " + studentInfo.getUniversityNumber());
		                System.out.println("Name: " + studentInfo.getName());
		                System.out.println("Sex: " + studentInfo.getSex());
		                System.out.println("department: " + studentInfo.getDepartment()); 
		                System.out.println("room: " + studentInfo.getRoom());
		                System.out.println("phone_number: " + studentInfo.getPhoneNumber());
		                System.out.println("address: " + studentInfo.getAddress());

		            }		            
		       }catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   return studentList; 
   }
   //입퇴실 신청
   public boolean student_inandout_request(StudentInformationDTO info, String inout) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "INSERT INTO student_Checkinandout(university_number, ing, in_out) VALUES (?, ?, ?)";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, info.getUniversityNumber());
	            pstmt.setString(2, "미승인");
	            pstmt.setString(3, inout);

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return true;
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return false;
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }

	    return false;
	}
   //입퇴실 조회
   public StudentCheckinandoutDTO inandout_search(StudentInformationDTO info) {
	   StudentCheckinandoutDTO check = new StudentCheckinandoutDTO();
	   String sql = "";
	   if(info.getUniversityNumber().equals("")) { // 학번이 없을때
		   sql = "SELECT * FROM student_Checkinandout";
	   }
	   else { //
		   sql = "SELECT * FROM student_Checkinandout WHERE university_number = '"+info.getUniversityNumber()+"'";
	   }
	   
	   try { 
		   ResultSet result = st.executeQuery(sql);
		
		   if(result.next()) {
		    	check.setUniversityNumber(result.getString("university_number"));
		    	check.setStartDate(result.getString("start_date"));
		    	check.setIng(result.getString("ing"));
		    	check.setInOut(result.getString("in_out"));
		    	System.out.println(result.getString("start_date"));
		    	System.out.println(result.getString("ing"));
			
		    	System.out.println("입퇴실 조회 성공");
		    	return check;
		   }else {
			   System.out.println("입퇴실 조회 실패");
			   return check;
		   }
	      } catch(Exception e) {
	         System.out.println("조회 실패 > "+e.toString());
	      }
	      return check;
	   }
   //학생 정보, 입퇴실 조회
   public List<StudentInfoCheckDTO> Search_Student_InfoCheck(StudentInformationDTO info){
	   List<StudentInfoCheckDTO> studentList = new ArrayList<>();
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	       
	       String sql = "";
	       
	       if (!info.getUniversityNumber().equals("") && !info.getUniversityNumber().equals("")) { // 둘다 입력
	    	    sql = "select * from student_Checkinandout, student_information where student_information.university_number=student_Checkinandout.university_number and student_information.university_number = '"
	    	    + info.getUniversityNumber() + "' and student_information.name ='" + info.getName() +"'";
	    	}
	       else if(!info.getUniversityNumber().equals("") && info.getName().equals("")) { //학번만 입력
	    	   sql = "select * from student_Checkinandout, student_information where student_information.university_number=student_Checkinandout.university_number and student_information.university_number = '"
	   	    	    + info.getUniversityNumber() + "'";
	       }
	       else if(info.getUniversityNumber().equals("") && !info.getName().equals("")) { // 이름만 입력
	    	   sql = "select * from student_Checkinandout, student_information where student_information.university_number=student_Checkinandout.university_number and student_information.name ='" + info.getName() +"'";
	       }
	       else { // 아무것도 입력 안함
	    	   sql = "select * from student_Checkinandout, student_information where student_information.university_number=student_Checkinandout.university_number";
	    	}
	       
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       try { 
	    	   ResultSet resultSet = st.executeQuery(sql);
		       	
		            while (resultSet.next()) {
		            	StudentInfoCheckDTO studentInfo = new StudentInfoCheckDTO();

		                 // Retrieve values from the ResultSet and set them in the DTO
		            	//String universityNumber, String startDate, String ing, String inOut,
		                //String name, String sex, String department, String address, String phoneNumber, String birth
		            	studentInfo.setUniversityNumber(resultSet.getString("student_Checkinandout.university_number"));
		                studentInfo.setName(resultSet.getString("student_information.name"));
		                studentInfo.setStartDate(resultSet.getString("student_Checkinandout.start_date"));
		                studentInfo.setIng(resultSet.getString("student_Checkinandout.ing"));
		                studentInfo.setInOut(resultSet.getString("student_Checkinandout.in_out"));
		                studentInfo.setSex(resultSet.getString("student_information.sex"));
		                studentInfo.setDepartment(resultSet.getString("student_information.department"));
		                studentInfo.setAddress(resultSet.getString("student_information.address"));
		                studentInfo.setPhoneNumber(resultSet.getString("student_information.phone_number"));
		                studentInfo.setBirth(resultSet.getString("student_information.birth"));
		                 // Add the DTO to the list
		                studentList.add(studentInfo);

		            }		            
		       }catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   return studentList; 
   }
 //학생 점수 등록
   public String student_score_insert(StudentRecordDTO record) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "INSERT INTO student_record (university_number, name, reason, score) VALUES (?, ?, ?, ?)";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, record.getUniversityNumber());
	            pstmt.setString(2, record.getName());
	            pstmt.setString(3, record.getReason());
	            pstmt.setString(4, record.getScore());

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }

	    return "실패";
	}
   
   
   //외박 수정
   public String outside_repair(String university_number, String reason, String content, String outing_date, String returning_date) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "UPDATE student_outside SET reason=?, content=?, outing_date=?, returning_date=? WHERE university_number=?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(5, university_number);
	            pstmt.setString(1, reason);
	            pstmt.setString(2, content);
	            pstmt.setString(3, outing_date);
	            pstmt.setString(4, returning_date);

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	return "실패";
	   
   }
   //외박 삭제 버튼을 누르면 정보 삭제
   public String outside_request_delete(String university_number, String reason, String content, String outing_date, String returning_date) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "delete from student_outside where university_number = ?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        	pstmt.setString(1, university_number);

	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삭제되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삭제 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	   return "실패";
   }
   //학생 정보와 생활관 정보 조인한 후에 정보 출력
   public List<SrudentInfoRoomDTO> Search_Student_Info(String value,String check){
	   List<SrudentInfoRoomDTO> studentList = new ArrayList<>();
	   
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	       String sql="";
	       if(check.equals("학번")) {
	    	   sql = "SELECT * FROM student_information "
		    	        + "JOIN student_room ON student_information.university_number = student_room.university_number "
		    	        + "WHERE student_information.university_number = ?";
	       }
	       else if(check.equals("이름")) {
	    	   sql = "SELECT * FROM student_information "
		    	        + "JOIN student_room ON student_information.university_number = student_room.university_number "
		    	        + "WHERE student_room.name = ?";
	       }
	       else if(check.equals("호수")) {
	    	   sql = "SELECT * FROM student_information "
		    	        + "JOIN student_room ON student_information.university_number = student_room.university_number "
		    	        + "WHERE student_room.room = ?";
	       }
	
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       
	       try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	       	pstmt.setString(1, value);
	       	ResultSet resultSet = pstmt.executeQuery();
	            
	            while (resultSet.next()) {
	            	SrudentInfoRoomDTO studentInfo = new SrudentInfoRoomDTO();

	                 // Retrieve values from the ResultSet and set them in the DTO
	                 studentInfo.setUniversityNumber(resultSet.getString("university_number"));
	                 studentInfo.setName(resultSet.getString("name"));
	                 studentInfo.setSex(resultSet.getString("sex"));
	                 studentInfo.setDepartment(resultSet.getString("department"));
	                 studentInfo.setAddress(resultSet.getString("address"));
	                 studentInfo.setPhoneNumber(resultSet.getString("phone_number"));
	                 studentInfo.setBirth(resultSet.getString("birth"));
	                 studentInfo.setRoom(resultSet.getString("room"));
	                 studentInfo.setStartDate(resultSet.getString("start_date"));
	                 studentInfo.setEndDate(resultSet.getString("end_date"));
	                 
	                 // Add the DTO to the list
	                 studentList.add(studentInfo);

	                 // Now you can use the studentInfo object as needed
	                 System.out.println("University Number: " + studentInfo.getUniversityNumber());
	                 System.out.println("Name: " + studentInfo.getName());
	                 System.out.println("Sex: " + studentInfo.getSex());
	                 System.out.println("Department: " + studentInfo.getDepartment());
	                 System.out.println("Address: " + studentInfo.getAddress());
	                 System.out.println("Phone Number: " + studentInfo.getPhoneNumber());
	                 System.out.println("Birth: " + studentInfo.getBirth());
	                 System.out.println("Room: " + studentInfo.getRoom());
	                 System.out.println("Start_Date: " + studentInfo.getStartDate());
	                 System.out.println("End_Date: " + studentInfo.getEndDate());
	            }
	            
	       } catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	       
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   return studentList;
   }
   //외박 신청 시 데이터베이스에 정보 저장
   public String outside_request(String university_number, String reason, String content, String outing_date, String returning_date) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "INSERT INTO student_outside (university_number, reason, content, outing_date, returning_date) values (?, ?, ?, ?, ?)";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, university_number);
	            pstmt.setString(2, reason);
	            pstmt.setString(3, content);
	            pstmt.setString(4, outing_date);
	            pstmt.setString(5, returning_date);

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }

	    return "실패";
	}
   //외박 학생 정보 조회
   
   //외박 승인됐는지 확인
   public String outside_manage_repair(String university_number, String success) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "UPDATE student_outside SET success = ? WHERE university_number = ?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(2, university_number);
	            pstmt.setString(1, success);

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	return "실패";
	   
   }
   //외박 승인,거절
   public String outside_manage_delete(String university_number) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "delete from student_outside where university_number = ? AND success = ?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        	pstmt.setString(1, university_number);
	        	pstmt.setString(2, "X");

	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삭제되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삭제 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	   return "실패";
   }
   //학생 정보 등록
   public String student_info_insert(String university_number, String name, String sex, String department, String address, String phone_number, String birth) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "INSERT INTO student_information (university_number, name, sex, department, address, phone_number, birth) values (?, ?, ?, ?, ?, ?, ?)";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, university_number);
	            pstmt.setString(2, name);
	            pstmt.setString(3, sex);
	            pstmt.setString(4, department);
	            pstmt.setString(5, address);
	            pstmt.setString(6, phone_number);
	            pstmt.setString(7, birth);

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }

	    return "실패";
	}
   //학생 정보 전체조회
   public List<SrudentInfoRoomDTO> Search_Student_Info1(){
	   List<SrudentInfoRoomDTO> studentList = new ArrayList<>();
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	
	       String sql = "SELECT * FROM student_information,student_room where student_information.university_number=student_room.university_number and student_information.name != ?";
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       try (PreparedStatement pstmt = con.prepareStatement(sql)) { 
	    	   pstmt.setString(1, "");
		       	ResultSet resultSet = pstmt.executeQuery();
		       	
		            while (resultSet.next()) {
		            	SrudentInfoRoomDTO studentInfo = new SrudentInfoRoomDTO();

		                 // Retrieve values from the ResultSet and set them in the DTO
		            	studentInfo.setUniversityNumber(resultSet.getString("university_number"));
		                studentInfo.setName(resultSet.getString("name"));
		                studentInfo.setSex(resultSet.getString("sex"));
		                studentInfo.setDepartment(resultSet.getString("department"));
		                studentInfo.setAddress(resultSet.getString("address"));
		                studentInfo.setPhoneNumber(resultSet.getString("phone_number"));
		                studentInfo.setBirth(resultSet.getString("birth"));
		                studentInfo.setRoom(resultSet.getString("room"));
		                 
		                 // Add the DTO to the list
		                studentList.add(studentInfo);

		                 // Now you can use the studentInfo object as needed
		                System.out.println("University Number: " + studentInfo.getUniversityNumber());
		                System.out.println("Name: " + studentInfo.getName());
		                System.out.println("Sex: " + studentInfo.getSex());
		                System.out.println("Department: " + studentInfo.getDepartment()); 
		                System.out.println("Address: " + studentInfo.getAddress());
		                System.out.println("PhoneNumber: " + studentInfo.getPhoneNumber());
		                System.out.println("Birth: " + studentInfo.getBirth());
		                System.out.println("room: " + studentInfo.getRoom());
		            }		            
		       }catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   return studentList; 
   }
   //학생 전체 정보 학번 조회
   public StudentInformationDTO Search_Student_Info2(StudentInformationDTO info){
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	
	       String sql = "select * from student_information where university_number = ? ";
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       try (PreparedStatement pstmt = con.prepareStatement(sql)) { 
	    	   pstmt.setString(1, info.getUniversityNumber());
		       	ResultSet resultSet = pstmt.executeQuery();
		       	
		            while (resultSet.next()) {

		                 // Retrieve values from the ResultSet and set them in the DTO
		            	info.setUniversityNumber(resultSet.getString("university_number"));
		            	info.setName(resultSet.getString("name"));
		            	info.setSex(resultSet.getString("sex"));
		            	info.setDepartment(resultSet.getString("department"));
		            	info.setAddress(resultSet.getString("address"));
		            	info.setPhoneNumber(resultSet.getString("phone_number"));
		            	info.setBirth(resultSet.getString("birth"));

		                 // Now you can use the studentInfo object as needed
		                System.out.println("University Number: " + info.getUniversityNumber());
		                System.out.println("Name: " + info.getName());
		                System.out.println("Sex: " + info.getSex());
		                System.out.println("Department: " + info.getDepartment()); 
		                System.out.println("Address: " + info.getAddress());
		                System.out.println("PhoneNumber: " + info.getPhoneNumber());
		                System.out.println("Birth: " + info.getBirth());
		            }		            
		       }catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   return info; 
   }
   //학생 정보 수정
   public String student_info_repair(String university_number, String name, String sex, String department, String address, String phone_number, String birth,String room) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "UPDATE student_information SET name=?, sex=?, department=?, address=?, phone_number=?, birth=? WHERE university_number=?";
	        String sql1 = "UPDATE student_room SET room=? WHERE university_number=?";
	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql);
	        		PreparedStatement pstmt1 = con.prepareStatement(sql1)) {
	            pstmt.setString(7, university_number);
	            pstmt.setString(1, name);
	            pstmt.setString(2, sex);
	            pstmt.setString(3, department);
	            pstmt.setString(4, address);
	            pstmt.setString(5, phone_number);
	            pstmt.setString(6, birth);

	            pstmt1.setString(1,room);
	            pstmt1.setString(2,university_number);
	            
	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();
	            int affectedRowsCheckinandout = pstmt1.executeUpdate();

	            if (affectedRows>0 && affectedRowsCheckinandout > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	return "실패";
	   
   }
   //학생 정보 삭제
   public String student_info_delete(String university_number, String b1, String c1, String d1, String e1, String f1, String g1, String I1) {
	    try {
	        // JDBC 드라이버 로드
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        // 데이터베이스 연결 매개변수
	        String jdbcUrl = "jdbc:mysql://localhost:3306/domitory";
	        String username = "root";
	        String password = "1234";

	        try (Connection con = DriverManager.getConnection(jdbcUrl, username, password)) {
	            String sql = "DELETE FROM student_information WHERE university_number = ?";

	            System.out.println("SQL Query: " + sql); // 쿼리 확인

	            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	                pstmt.setString(1, university_number);

	                int affectedRows = pstmt.executeUpdate();

	                if (affectedRows > 0) {
	                    System.out.println("데이터가 성공적으로 삭제되었습니다.");
	                    return "성공";
	                } else {
	                    System.out.println("데이터 삭제 실패");
	                    return "실패";
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                JOptionPane.showInputDialog(null, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	    return "실패";
	}
   
   
 //학생 점수 정보 수정
   public String student_Record_repair(StudentRecordDTO record,String prereason) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "UPDATE student_record SET name=?, reason=?, score=? WHERE university_number=? and reason=?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, record.getName());
	            pstmt.setString(2, record.getReason());
	            pstmt.setString(3, record.getScore());
	            pstmt.setString(4, record.getUniversityNumber());
	            pstmt.setString(5, prereason);
	            
	            

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 수정되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 점수 수정 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	return "실패";
	   
   }
   //학번이용해서 점수 테이블 이름 확인
   public String record_student_check(String number) {
	      String name = "";
	      try {
	         String sql = "SELECT name FROM student_information WHERE university_number = ?";
	         PreparedStatement preparedStatement = con.prepareStatement(sql);
	         
			 preparedStatement.setString(1, number);    
			 ResultSet result = preparedStatement.executeQuery();

	            if(result.next()) {
	            	name = result.getString("name");
	               System.out.println("학번 확인 성공");
	            }
	            
	            else {
	               System.out.println("학번 확인 실패");
	            }
	      } catch(Exception e) {
	         System.out.println("로그인 실패 > "+e.toString());
	      }
	      return name;
	   }
   //입퇴실 승인,거절
   public String inandout_manage_repair(String university_number, String success) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "UPDATE student_Checkinandout SET ing = ? WHERE university_number = ?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(2, university_number);
	            pstmt.setString(1, success);

	            // executeUpdate() 메소드를 사용하여 INSERT 문 실행
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삽입되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삽입 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	return "실패";
	   
   }
   //입퇴실 삭제
   public String inandout_manage_delete(String university_number) {
	   try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");

	        String sql = "delete from student_Checkinandout where university_number = ? AND ing = ?";

	        System.out.println("SQL Query: " + sql); // 쿼리 확인

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        	pstmt.setString(1, university_number);
	        	pstmt.setString(2, "미승인");

	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("데이터가 성공적으로 삭제되었습니다.");
	                return "성공";
	            } else {
	                System.out.println("데이터 삭제 실패");
	                return "실패";
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	        }

	    } catch (Exception e) {
	        System.out.println("MySQL 서버 연동 실패 > " + e.toString());
	    }
	   return "실패";
   }
   public List<StudentOutsideDTO> Search_Outside_Info(){
	   List<StudentOutsideDTO> studentList = new ArrayList<>();
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	       System.out.println("JDBC 드라이버가 성공적으로 로드되었습니다");
	
	       String sql = "select * from student_outside where success = ?";
	
	       System.out.println("SQL Query: " + sql); // 쿼리 확인
	       try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	    	   String success = "X";
		       	pstmt.setString(1, success);
		       	ResultSet resultSet = pstmt.executeQuery();
		            
		            while (resultSet.next()) {
		            	StudentOutsideDTO studentInfo = new StudentOutsideDTO();

		                 // Retrieve values from the ResultSet and set them in the DTO
		            	studentInfo.setUniversityNumber(resultSet.getString("university_number"));
		                studentInfo.setReason(resultSet.getString("reason"));
		                studentInfo.setContent(resultSet.getString("content"));
		                studentInfo.setTime(resultSet.getString("atime"));
		                studentInfo.setOutingDate(resultSet.getString("outing_date"));
		                studentInfo.setReturningDate(resultSet.getString("returning_date"));
		                studentInfo.setSuccess(resultSet.getString("success"));
		                 
		                 // Add the DTO to the list
		                studentList.add(studentInfo);

		                 // Now you can use the studentInfo object as needed
		                System.out.println("University Number: " + studentInfo.getUniversityNumber());
		                System.out.println("Name: " + studentInfo.getReason());
		                System.out.println("Content: " + studentInfo.getContent());
		                System.out.println("Atime: " + studentInfo.getTime()); 
		                System.out.println("OutingDate: " + studentInfo.getOutingDate());
		                System.out.println("ReturningDate: " + studentInfo.getReturningDate());
		                System.out.println("Success: " + studentInfo.getSuccess());
		            }		            
		       }catch (SQLException ex) {
	           ex.printStackTrace();
	           JOptionPane.showInputDialog(this, "데이터베이스에 연결 중 오류 발생: " + ex.getMessage());
	       }
	   }catch(Exception e) {
	         System.out.println("MySQL 서버 연동 실패 > "+e.toString());
	   }
	   return studentList; 
   }
}
package Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.List;

import Database.StudentInformationDTO;
import Database.StudentRecordDTO;

public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public Client() throws IOException {
        socket = new Socket("localhost", 12346);//"172.29.124.116", 12345
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    public boolean logIn(StudentInformationDTO information) throws IOException, ClassNotFoundException {

        String request = "로그인:" + information.getUniversityNumber() + ":" + information.getBirth();
        out.writeObject(request);

        try {
            // 서버가 데이터를 보낼 때까지 대기
            Object response = in.readObject();

            if (response instanceof String) {
                String result = (String) response;
                System.out.println(result);

                return result.equals("로그인 성공");
            }
        } catch (EOFException e) {
            // EOFException은 더 이상 읽을 데이터가 없을 때 발생하므로, 예외를 무시해도 됩니다.
        }

        return false;
    }
    public List<StudentRecordDTO> ScoreBring(StudentRecordDTO information) throws IOException, ClassNotFoundException {

        String request = "점수정보조회:" + information.getUniversityNumber() + ":" + information.getName();
        out.writeObject(request);
        List<StudentRecordDTO> result=null;
        try {
            // 서버가 데이터를 보낼 때까지 대기
        	//List<StudentInformationDTO>
            Object response = in.readObject();

            if (response instanceof List<?>) {
                result = (List<StudentRecordDTO>) response;
            }
        } catch (EOFException e) {
        	System.out.println(result);
            // EOFException은 더 이상 읽을 데이터가 없을 때 발생하므로ㄴ, 예외를 무시해도 됩니다.
        }
        System.out.println(result);
        return result;
    }
    //학번확인 이름 반환
    public String CheckNum(String UniversityNumber) throws IOException, ClassNotFoundException {

        String request = "학번확인:" + UniversityNumber ;
        out.writeObject(request);
        String result=null;
        try {
            // 서버가 데이터를 보낼 때까지 대기
            Object response = in.readObject();

            if (response instanceof String) {
                result = (String) response;
                System.out.println(result);

            }
        } catch (EOFException e) {
            // EOFException은 더 이상 읽을 데이터가 없을 때 발생하므로, 예외를 무시해도 됩니다.
        }

        return result;
    }
    public String CheckInsert(StudentRecordDTO record) throws IOException, ClassNotFoundException {

        String request = "점수추가:" + record.getUniversityNumber() + ":" + record.getName() 
        + record.getReason() + ":" + record.getScore() ;
        out.writeObject(request);
        String result=null;
        try {
            // 서버가 데이터를 보낼 때까지 대기
            Object response = in.readObject();

            if (response instanceof String) {
                result = (String) response;
                System.out.println(result);

            }
        } catch (EOFException e) {
            // EOFException은 더 이상 읽을 데이터가 없을 때 발생하므로, 예외를 무시해도 됩니다.
        }

        return result;
    }
    public String CheckUpdate(StudentRecordDTO record,String prereason) throws IOException, ClassNotFoundException {

        String request = "수정:" + record.getUniversityNumber() + ":" + record.getName() 
        + record.getReason() + ":" + record.getScore() + ":" + prereason;
        out.writeObject(request);
        String result=null;
        try {
            // 서버가 데이터를 보낼 때까지 대기
            Object response = in.readObject();

            if (response instanceof String) {
                result = (String) response;
                System.out.println(result);

            }
        } catch (EOFException e) {
            // EOFException은 더 이상 읽을 데이터가 없을 때 발생하므로, 예외를 무시해도 됩니다.
        }

        return result;
    }
    public boolean CheckDelete(StudentRecordDTO record) throws IOException, ClassNotFoundException {

        String request = "삭제:" + record.getUniversityNumber() + ":" + record.getName() 
        + record.getReason() + ":" + record.getScore();
        out.writeObject(request);
        String result=null;
        try {
            // 서버가 데이터를 보낼 때까지 대기
            Object response = in.readObject();

            if (response instanceof String) {
                result = (String) response;
                return result.equals("데이터 점수 삭제 성공");

            }
        } catch (EOFException e) {
            // EOFException은 더 이상 읽을 데이터가 없을 때 발생하므로, 예외를 무시해도 됩니다.
        }

        return false;
    }
    public void endServer() {
       try {
         if (socket != null) socket.close();
         System.out.println("서버연결종료");
      } catch (IOException e) {
         System.out.println("소켓통신에러");
      }
    }
}
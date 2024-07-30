package user;

import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    private final String driver = "oracle.jdbc.driver.OracleDriver";
    private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String user = "C##java";
    private final String password = "oracle";

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private static UserDAO instance = new UserDAO();

    public UserDAO() {
        try{
            Class.forName(driver);
            System.out.println("driver loading");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static UserDAO getInstance() {
        return instance;
    }

    public void getConnection(){
        try {
            conn = DriverManager.getConnection(url,user,password);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            if(rs != null){rs.close();}
            if(pstmt!=null){pstmt.close();}
            if(conn!=null){conn.close();}
            System.out.println("connection closed");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void checkDuplicate(String id, String password){
        try{
            pstmt = conn.prepareStatement("select * from member where id=? and password=?");
            pstmt.setString(1,id);
            pstmt.setString(2,password);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void join(UserDTO userDTO){
        this.getConnection();
        try{
            checkDuplicate(userDTO.getId(),userDTO.getPassword());
            rs = pstmt.executeQuery();

            if(rs.next()){
                System.out.println("이미 기존에 있는 회원입니다.");
            }
            else{
                try{
                    pstmt = conn.prepareStatement("insert into member values (?,?,?)");
                    pstmt.setString(1,userDTO.getId());
                    pstmt.setString(2, userDTO.getPassword());
                    pstmt.setString(3, String.valueOf(userDTO.getAge()));
                    rs = pstmt.executeQuery();

                    if(rs.next()){
                        System.out.println("회원 가입이 완료되었습니다.");
                    }
                    else{
                        System.out.println("회원가입에 실패하였습니다.");
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        closeConnection();
    }

    public void login(String id, String password){
        this.getConnection();
        try{
            checkDuplicate(id,password);
            rs = pstmt.executeQuery();

            if(rs.next()){
                System.out.println("로그인에 성공합니다.");
            }
            else{
                System.out.println("존재하지 않는 회원입니다.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

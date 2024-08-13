package jdbc;

import reservation.ReservationDTO;
import users.UsersDTO;

import java.sql.*;

public class JdbcDAO {
    private final String driver = "oracle.jdbc.driver.OracleDriver";
    private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String user = "c##java";
    private final String password = "1234";

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private static JdbcDAO instance = new JdbcDAO();

    public JdbcDAO() {
        try {
            Class.forName(driver);
            System.out.println("driver loading");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static JdbcDAO getInstance() {
        return instance;
    }

    public void getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // USERS
    public void checkDuplicate(String id) {
        try {
            pstmt = conn.prepareStatement("select * from users where user_id=?");
            pstmt.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkPassword(String id, String password) {
        try {
            pstmt = conn.prepareStatement("select * from users where user_id=? And password=?");
            pstmt.setString(1, id);
            pstmt.setString(2, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean join(UsersDTO usersDTO) {
        this.getConnection();
        boolean result = false;
        try {
            checkDuplicate(usersDTO.getUserId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("이미 가입된 아이디입니다.\n");
            } else {
                try {
                    pstmt = conn.prepareStatement("INSERT INTO users (user_id, password, age) VALUES (?, ?, ?)");
                    pstmt.setString(1, usersDTO.getUserId());
                    pstmt.setString(2, usersDTO.getUserPassword());
                    pstmt.setString(3, String.valueOf(usersDTO.getUserAge()));


                    int su = pstmt.executeUpdate();
                    if (su > 0) {
                        System.out.println("회원 가입이 완료되었습니다.\n");
                        result = true;

                    } else {
                        System.out.println("회원가입에 실패하였습니다.\n");
                        result = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return result;

    }

    public boolean login(String id, String password) {
        this.getConnection();
        boolean result = false;
        try {
            checkPassword(id, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("로그인에 성공합니다.\n");
                result = true;
            } else {
                System.out.println("아이디 혹은 비밀번호가 맞지 않습니다.\n");
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return result;
    }


    public void showTheaterList() {
        this.getConnection();
        try {
            pstmt = conn.prepareStatement("select * from theater");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("THEATER_ID") + ". " + rs.getString("NAME")
                        + " (" + rs.getString("LOCATION") + ")");
            }

            System.out.println("영화관을 선택하세요 : ");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void showMovieList() {
        this.getConnection();
        try {
            pstmt = conn.prepareStatement("select * from movie");
            rs = pstmt.executeQuery();


            while (rs.next()) {
                System.out.println(rs.getString("MOVIE_ID") + ". " + rs.getString("TITLE")
                        + " ( 장르 : " + rs.getString("GENRE") + ", " + rs.getString("LIMIT_AGE") + "세 이용 관가)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void showTimeTable() {
        this.getConnection();
        try {
            pstmt = conn.prepareStatement("SELECT TO_CHAR(start_time, 'HH24:MI') " +
                    "AS start_time FROM showTime GROUP BY TO_CHAR(start_time, 'HH24:MI')");

            rs = pstmt.executeQuery();
            int i = 1;
            while (rs.next()) {
                System.out.println(i + ". 상영 시작 시간: " + rs.getString("start_time"));
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public int getShowtimeId(int movieId, int theaterId, String timeString) {
        int showtimeId = -1; // -1로 잡은 이유 무엇
        this.getConnection();
        try {
            String sql = "SELECT showtime_id FROM Showtime WHERE movie_id = ? AND theater_id = ? AND TO_CHAR(START_TIME, 'HH24:MI') = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, theaterId);
            pstmt.setString(3, timeString);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                showtimeId = rs.getInt("showtime_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return showtimeId;
    }


    public void showSeatsTable(int showtimeId) {
        this.getConnection();
        String sql = "select seat_id,status from showtime_seat where showtime_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, showtimeId);
            rs = pstmt.executeQuery();
            int enter = 0;
            char nextChar = 'A';

            System.out.println("   01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20");
            System.out.print(nextChar + "  ");
            while (rs.next()) {

                if (enter == 20) {
                    enter = 0;
                    nextChar++;
                    System.out.println();
                    System.out.print(nextChar + "  ");
                }
                switch (rs.getInt("status")) {
                    case 1:
                        System.out.print(" O ");
                        enter++;
                        break;

                    default:
                        System.out.print(" X ");
                        enter++;
                        break;

                }

            }
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }


    public boolean reservation(ReservationDTO reservationDTO, int showtimeId, String seatRow, int seatNum) {
        this.getConnection();
        int seat_id = -1;
        int showtime_seat_id = -1;
        boolean reserved = false;
        try {
            pstmt = conn.prepareStatement("select seat_id from seats where theater_id = ? and seat_row = ? and seat_number = ?");
            pstmt.setInt(1, reservationDTO.getTheaterId());
            pstmt.setString(2, seatRow);
            pstmt.setInt(3, seatNum);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                seat_id = rs.getInt("seat_id");
                System.out.println(seat_id);
            }

            pstmt = conn.prepareStatement("select showtime_seat_id from showtime_seat where showtime_id=? and seat_id = ? and status = 1");
            pstmt.setInt(1, showtimeId);
            pstmt.setInt(2, seat_id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                showtime_seat_id = rs.getInt("showtime_seat_id");
                System.out.println(showtime_seat_id);
                pstmt = conn.prepareStatement("UPDATE Showtime_Seat SET status = 0 WHERE showtime_seat_id = ?");
                pstmt.setInt(1, showtime_seat_id);
                pstmt.execute();

                pstmt = conn.prepareStatement("INSERT INTO reservation VALUES (reservation_seq.nextval,?, ?, sysdate,1)");
                pstmt.setInt(1, showtime_seat_id);
                pstmt.setString(2, reservationDTO.getUserId());
                pstmt.execute();
                reserved = true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserved;
    }
}

package reservation;

import jdbc.JdbcDAO;

import java.util.Scanner;

public class ReservationServiceImpl implements ReservationService {
    private Scanner sc = new Scanner(System.in);
    private int num;
    private ReservationDTO.ShowtimeIdDTO showtimeIdDTO = new ReservationDTO.ShowtimeIdDTO();
    private int showtimeId;

    @Override
    public void selectTheater() {
        System.out.println();
        JdbcDAO.getInstance().showTheaterList();
        System.out.print("영화관을 선택하세요 : ");
        int theaterId = sc.nextInt();
        showtimeIdDTO.setTheaterId(theaterId);
    }

    @Override
    public void selectMovie() {
        System.out.println();
        JdbcDAO.getInstance().showMovieList();
        System.out.print("영화를 선택하세요 : ");
        int movieId = sc.nextInt();
        showtimeIdDTO.setMovieId(movieId);

    }

    @Override
    public void selectShowTime() {
        System.out.println();
        JdbcDAO.getInstance().showTimeTable();
        System.out.print("상영 시간을 선택하세요: ");
        int timeChoice = sc.nextInt();

        String timeString;
        switch (timeChoice) {
            case 1:
                timeString = "08:00";
                break;
            case 2:
                timeString = "16:00";
                break;
            case 3:
                timeString = "21:00";
                break;
            default:
                throw new IllegalArgumentException("옳지 않은 입력값입니다");
        }

        showtimeIdDTO.setTime(timeString);
        showtimeId = JdbcDAO.getInstance().getShowtimeId(showtimeIdDTO.getMovieId(), showtimeIdDTO.getTheaterId(), timeString);
    }

    @Override
    public void insertPeopleNumber() {
        System.out.print("예매할 인원 수를 입력하세요 : ");
        num = sc.nextInt();
        System.out.println(num + "명의 좌석선택을 진행합니다");

    }

    @Override
    public void selectSeats() {
        boolean reserved = false;
        for (int i = 0; i < num; i++) {

            while (true) {

                JdbcDAO.getInstance().showSeatsTable(showtimeId);
                System.out.print("좌석의 행을 입력하세요(A,B,C,,,G) : ");
                String seatRow = sc.next();
                System.out.print("좌석의 열을 입력하세요(1,2,3,,,20) : ");
                int seatNum = sc.nextInt();


                reserved = JdbcDAO.getInstance().reservation(showtimeIdDTO, showtimeId, seatRow, seatNum);

                if (reserved) {
                    System.out.println("예약 완료");
                    break;

                } else {
                    System.out.println("==============================");
                    System.out.println("\t이미 예약된 좌석입니다\t");
                    System.out.println("==============================");

                }
            }
        }


    }


    @Override
    public void calCharge() {

    }

    @Override
    public void cancelReservation(int reservationId) {

    }
}

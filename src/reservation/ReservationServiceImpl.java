package reservation;

import jdbc.JdbcDAO;

import java.util.Scanner;

public class ReservationServiceImpl implements ReservationService{
    private Scanner sc = new Scanner(System.in);
    private int num;
    private ReservationDTO.ShowtimeIdDTO showtimeIdDTO = new ReservationDTO.ShowtimeIdDTO();
    private int showtimeId;

    @Override
    public void selectTheater() {
        JdbcDAO.getInstance().showTheaterList();
        System.out.print("영화관을 선택하십쇼: ");
        int theaterId = sc.nextInt();
        showtimeIdDTO.setTheaterId(theaterId);
    }

    @Override
    public void selectMovie() {
        JdbcDAO.getInstance().showMovieList();
        System.out.print("영화를 선택하십쇼: ");
        int movieId = sc.nextInt();
        showtimeIdDTO.setMovieId(movieId);

    }

    @Override
    public void selectShowTime() {
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
                throw new IllegalArgumentException("옳지 않은 입력값");
        }

        showtimeIdDTO.setTime(timeString);
        showtimeId = JdbcDAO.getInstance().getShowtimeId(showtimeIdDTO.getMovieId(), showtimeIdDTO.getTheaterId(), timeString);
    }

    @Override
    public void insertPeopleNumber() {
        System.out.print("인원 수를 입력하세요(자신을 포함) :");


        num = sc.nextInt();

        
    }

    @Override
    public void selectSeats() {
        for(int i=0;i<num;i++){
            JdbcDAO.getInstance().showSeatsTable(showtimeId);
            System.out.println("좌석을 선택해주세요(대소문자 상관x): ");

        }


    }

    @Override
    public void calCharge() {

    }
    @Override
    public void cancelReservation(int reservationId) {

    }
}

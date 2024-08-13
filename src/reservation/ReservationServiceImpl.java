package reservation;

import jdbc.JdbcDAO;

import java.util.Scanner;

public class ReservationServiceImpl implements ReservationService {
    private Scanner sc = new Scanner(System.in);
    private int num;
    private ReservationDTO reservationDTO = new ReservationDTO();
    private int showtimeId;


    @Override
    public void reservationTask(String resultId) {
        reservationDTO.setUserId(resultId);
        selectTheater();
        selectMovie();
        selectShowTime();
        insertPeopleNumber();
        selectSeats();
        //System.out.println("디버깅"+"\n"+reservationDTO.getMovieId()+"\n"+reservationDTO.getTheaterId()+"\n"+reservationDTO.getTime()+"\n"+reservationDTO.getUserId());

        calCharge();

    }

    @Override
    public void selectTheater() {
        System.out.println();
        JdbcDAO.getInstance().showTheaterList();
        int theaterId = sc.nextInt();
        reservationDTO.setTheaterId(theaterId);
    }

    @Override
    public void selectMovie() {
        System.out.println();
        JdbcDAO.getInstance().showMovieList();
        System.out.print("영화를 선택하세요 : ");
        int movieId = sc.nextInt();
        reservationDTO.setMovieId(movieId);

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

        reservationDTO.setTime(timeString);
        showtimeId = JdbcDAO.getInstance().getShowtimeId(reservationDTO.getMovieId(), reservationDTO.getTheaterId(), timeString);
    }

    @Override
    public void insertPeopleNumber() {
        System.out.print("예매할 인원 수를 입력하세요 : ");
        num = sc.nextInt();
        System.out.println(num + "명의 좌석선택을 진행합니다");
        //나이 입력 받고 예외처리 필요

    }

    @Override
    public void selectSeats() {
        boolean reserved = false;
        for (int i = 0; i < num; i++) {

            while (true) {

                JdbcDAO.getInstance().showSeatsTable(showtimeId);
                System.out.print("좌석을 입력하세요(A1,A2,,,): ");
                String seat = sc.next().toUpperCase();
                String seatRow = seat.substring(0, 1);
                int seatNum = Integer.parseInt(seat.substring(1));

                //예외처리 필요

                reserved = JdbcDAO.getInstance().reservation(reservationDTO, showtimeId, seatRow, seatNum);

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
        int charge = 0;
        if(reservationDTO.getTime().equals("08:00")){
            charge = 7500;
        }
        else{
            charge = 15000;
        }
        System.out.println("==============================");
        System.out.println();
        System.out.println(num+"명의 요금은 "+num*charge+"원 입니다.");


    }


    @Override
    public void cancelReservation(int reservationId) {

    }


}

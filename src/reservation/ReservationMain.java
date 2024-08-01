package reservation;


// 이거는 나중에 mainservice에서 수행해야댐
public class ReservationMain {
    public static void main(String[] args) {
        ReservationServiceImpl reservationService = new ReservationServiceImpl();
        reservationService.selectTheater();
        reservationService.selectMovie();
        reservationService.selectShowTime();
       // reservationService.insertPeopleNumber();
        reservationService.selectSeats();
    }
}

package reservation;

public interface ReservationService {

    void reservationTask(String resultId);

    void selectTheater();

    void selectMovie();

    void selectShowTime();

    void insertPeopleNumber();

    void selectSeats();

    void calCharge();

    void cancelReservation(int reservationId);

}
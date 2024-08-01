package reservation;

public interface ReservationService {
    public void selectTheater();

    public void selectMovie();

    public void selectShowTime();

    public void insertPeopleNumber();

    public void selectSeats();

    public void calCharge();
    public void cancelReservation(int reservationId);
}
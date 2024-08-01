package reservation;

import lombok.Data;

import java.util.Date;

public class ReservationDTO {

    @Data
    public static class ShowtimeIdDTO {
        private int movieId;
        private int theaterId;
        private String time;
    }

}

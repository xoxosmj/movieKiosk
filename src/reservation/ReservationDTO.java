package reservation;

import lombok.Data;

@Data
public class ReservationDTO {

    /*
    @Data
    public static class ShowtimeIdDTO {
        private int movieId;
        private int theaterId;
        private String time;
    }*/

        private String userId;
        private int movieId;
        private int theaterId;
        private String time;




}

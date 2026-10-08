package problems.MovieTicketBooking;

import java.util.List;
public class Reservation {
//    - confirmationId : String
//    - showtime : Showtime
//    - seatIds : List<String>
//
//    + Reservation -> constructor imp
//    + getConfirmationId() → string
//    + getSeatIds() → List<string>
//    + getShowtime() → Showtime

    String confirmationId;
    Showtime showtime;
    List<String> seatIds;

    public Reservation(String confirmationId, Showtime showtime, List<String> seatIds){
        this.confirmationId = confirmationId;
        this.showtime = showtime;
        this.seatIds = List.copyOf(seatIds);
    }

    public String getConfirmationId(){
        return confirmationId;
    }

    public Showtime getShowtime(){
        return showtime;
    }

    public List<String> getSeatIds(){
        return List.copyOf(seatIds);
    }

}

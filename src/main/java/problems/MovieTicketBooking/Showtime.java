package problems.MovieTicketBooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Showtime {
//    - id : String
//    - theater : Theater
//    - datetime : DateTime
//    - screenLabel : String
//    - movie : Movie
//    - reservations : List<Reservation>
//
//    + getId
//    + getTheater
//    + getDateTime
//    + getMovie
//    + isAvailable(seat) : boolean
//    + getAvailableSeats : List<String>
//    + book(reservation)
//    + cancel(reservation)

    String id;
    Theater theater;
    LocalDateTime dateTime;
    String screenLabel;
    Movie movie;
    List<Reservation> reservations;

    public String getId() {
        return id;
    }

    public Theater getTheater() {
        return theater;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getScreenLabel() {
        return screenLabel;
    }

    public Movie getMovie() {
        return movie;
    }

    public Showtime(String id, Theater theater, LocalDateTime dateTime, String screenLabel, Movie movie){
        this.id = id;
        this.dateTime =dateTime;
        this.theater = theater;
        this.screenLabel = screenLabel;
        this.movie = movie;
        this.reservations = new ArrayList<>();
    }

    public boolean isAvailable(String seatId){
        for(Reservation reservation : reservations){
            if(reservation.getSeatIds().contains(seatId)){
                return false;
            }
        }

        return true;
    }

    public List<String> getAvailableSeats(){
        Set<String> booked = new HashSet<>();
        for(Reservation reservation : reservations){
            for(String seatId : reservation.getSeatIds()){
                booked.add(seatId);
            }
        }

        List<String> available = new ArrayList<>();
        for(char ch = 'A'; ch <= 'Z'; ch++){
            for(int num = 0; num <= 20; num++){
                String seatId = String.valueOf(ch) + num;
                if(!booked.contains(seatId)){
                    available.add(seatId);
                }
            }
        }

        return available;
    }

    public void book(Reservation reservation) throws Exception {
        synchronized (this) {
            List<String> seatIds = reservation.getSeatIds();

            if (seatIds == null || seatIds.isEmpty()) {
                throw new Exception("Must select at least one seat");
            }

            // Validate all seats exist in the layout
            for (String seatId : seatIds) {
                if (!isValidSeatId(seatId)) {
                    throw new Exception("InvalidSeatException " + seatId);

                }
            }

            // Check if seats are available
            for (String seatId : seatIds) {
                if (!isAvailable(seatId)) {
                    throw new Exception("SeatUnavailableException " + seatId);
                }
            }

            // now reserve it - All check passed
            reservations.add(reservation);
        }
    }

    private boolean isValidSeatId(String seatId){
        char row = seatId.charAt(0);
        int num = Integer.parseInt(seatId.substring(1));
        if(row >= 'A' && row <= 'Z' && num >= 0 && num <= 20){
            return true;
        }
        return false;
    }

    public void cancel(Reservation reservation){
        synchronized (this){
            reservations.remove(reservation);
        }
    }

}
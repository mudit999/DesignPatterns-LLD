package problems.MovieTicketBooking;

import java.time.LocalDateTime;
import java.util.*;

public class BookingSystem {
//    - theaters : List<Theater>
//    - moviesById : Map<movieId, Movie>
//    - showtimesByMovieId : Map<movieId, List<Showtime>>
//    + searchMovies(title: String) : List<Showtime>
//    + getShowtimesAtTheater(theater) : List<Showtime>
//    + book(showtimeId, list of seats) : Reservation
//    + cancelReservation(confirmationId) : boolean

    List<Theater> theaters;
    Map<String, Movie> moviesById; // String-> movieId
    Map<String, List<Showtime>> showtimesByMovieId;
    Map<String, Showtime> showtimeById;
    Map<String, Reservation> reservationsById; // String -> confirmationId
    // constructor
    public BookingSystem(List<Theater> theaters){
        this.theaters = theaters;
        this.moviesById = new HashMap<>();
        this.showtimesByMovieId = new HashMap<>();
        this.showtimeById = new HashMap<>();
        this.reservationsById = new HashMap<>(); // empty initialization

        for(Theater theater : theaters){
            for(Showtime showtime : theater.getShowtimes()){
                Movie movie = showtime.getMovie();
                moviesById.put(movie.getId(), movie);
                showtimeById.put(showtime.getId(), showtime);

                // Group showtimes by movie for efficient search
                if(!showtimesByMovieId.containsKey(movie.getId())){
                    showtimesByMovieId.put(movie.getId(), new ArrayList<>());
                }
                showtimesByMovieId.get(movie.getId()).add(showtime);
            }
        }
    }

    public List<Showtime> searchMovies(String title){
        if(title == null || title == ""){
            return new ArrayList<>();
        }

        List<Showtime> results = new ArrayList<>();
        String searchLower = title.toLowerCase();
        LocalDateTime now = LocalDateTime.now();

        for(String movieId : moviesById.keySet()){
            Movie movie = moviesById.get(movieId);

            if(movie.getTitle().contains(title)){
//                showtimesByMovieId
                for(Showtime showtime : showtimesByMovieId.get(movieId)){
                    if(showtime.getDateTime().isAfter(now)){
                        results.add(showtime);
                    }
                }
            }
        }

        return results;
    }

    public List<Showtime> getShowtimesAtTheater(Theater theater){
        if(theaters == null){
            return new ArrayList<>();
        }

        List<Showtime> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for(Showtime showtime : theater.getShowtimes()){
            if(showtime.getDateTime().isAfter(now)){
                result.add(showtime);
            }
        }
        return result;
    }

//    book(showtimeId, list of seats)
    public String book(String showtimeId, List<String> seatIds) throws Exception {
        if(showtimeId == null || seatIds == null || seatIds.isEmpty()){
            throw new Exception("InvalidRequestException");
        }

        Showtime showtime = showtimeById.get(showtimeId);
        if(showtime == null){
            throw new Exception("ShowtimeNotFoundException");
        }

        // create reservation
        String confirmationId = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(confirmationId, showtime, seatIds);

        // Hand to showtime for atomic validation + storage
        showtime.book(reservation);

        // Register in routing index so cancelReservation can find it by confirmation ID
        reservationsById.put(confirmationId,reservation);
        return confirmationId;
    }

    public void cancel(String confirmationId) throws Exception {
        if(confirmationId == null || confirmationId == ""){
            throw new Exception("InvalidRequestException");

        }

        Reservation reservation = reservationsById.get(confirmationId);
        if(reservation == null){
            throw new Exception("NoReservationExistException");
        }

        Showtime showtime  = reservation.getShowtime();

        // Showtime removes the reservation and frees the seats atomically
        showtime.cancel(reservation);

        reservationsById.remove(confirmationId);
    }

}

package problems.MovieTicketBooking;

import java.util.ArrayList;
import java.util.List;

public class Theater {
//    - id
//    - name
//    - showtimes : List<Showtime>
//
//    + getShowtimes : List<Showtime>
//    + getShowtimeForMovie(movie) : List<Showtime>

    String id;
    String name;
    List<Showtime> showtimes;

    public Theater(String id, String name){
        this.id = id;
        this.name = name;
        this.showtimes = new ArrayList<>();
    }

    public List<Showtime> getShowtimes(){
        return showtimes;
    }

    public List<Showtime> getShowtimeForMovie(Movie movie){
        List<Showtime> result = new ArrayList<>();
        for(Showtime showtime : showtimes){
            if(movie.getId() == showtime.getMovie().getId()){
                result.add(showtime);
            }
        }

        return result;
    }

}

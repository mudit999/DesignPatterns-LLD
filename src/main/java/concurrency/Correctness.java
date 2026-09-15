package concurrency;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// C1 -> check then act
class BookingService{
    Map<String, Seat> seats;

    public boolean bookSeat(String seatId, String visitorId){
        Seat seat = seats.get(seatId);
        if(seat.isAvailable()){
            // trouble area
            seat.setOccupant(visitorId);
            return true;
        }
        return false;
    }
}

// FIX with lock
class BookingServiceFix{
    Map<String, Seat> seats;

    public boolean bookSeat(String seatId, String visitorId){
        Seat seat = seats.get(seatId);
        synchronized (true) {
            if (seat.isAvailable()) {
                // trouble area
                seat.setOccupant(visitorId);
                return true;
            }
            return false;
        }
    }
}




public class Correctness {
// C2 -> read modify write

    private int count = 0;
    public void increment(){
        count++; // read, +1, then write count
    }

    // FIX
    private AtomicInteger aCount = new AtomicInteger();
    public void incrementFix(){
        aCount.incrementAndGet(); // hardware level atomic operation
    }
}

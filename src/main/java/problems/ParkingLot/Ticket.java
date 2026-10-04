package problems.ParkingLot;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum TicketStatus{
    CLOSED, ACTIVE
}

public class Ticket{
// - ticketId : String
// - entryTime : Instant
// - exitTime : Instant?
// - vehicleId : String
// - spot : Spot
// - status : TicketStatus (enum)
// - price : Long?
// + close() : boolean

    String ticketId;
    Instant entryTime;
    Instant exitTime;
    Spot spot;
    TicketStatus status;
    Long price;
    String vehicleId;

    public Ticket(Vehicle vehicle, Spot spot){
        this.ticketId = UUID.randomUUID().toString();;
        this.entryTime = Instant.now();
        this.vehicleId = vehicle.vehicleId;
        this.spot = spot;
        this.status = TicketStatus.ACTIVE;
    }

    public Spot getspot(){
        return spot;
    }

    public Instant updateExitTime(){
        return this.exitTime = Instant.now();
    }

    public TicketStatus getStatus(){
        return this.status;
    }

    public Spot getSpot(){
        return this.spot;
    }

    public String getTicketId(){
        return ticketId;
    }

    private final Lock lock = new ReentrantLock();

    public boolean close(Long price) {
        lock.lock();
        try {
            // Someone already closed this ticket
            if (status == TicketStatus.CLOSED) {
                return false;
            }
            status = TicketStatus.CLOSED;
            return true;
        } finally {
            lock.unlock();
        }
    }
}

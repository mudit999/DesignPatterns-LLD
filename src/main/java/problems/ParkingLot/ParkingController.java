package problems.ParkingLot;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ParkingController{
    List<ParkingFloor> floors;
    Map<String, Ticket> tickets;
    Map<VehicleType, Long> hourlyPrice;
    public Ticket bookTicket(Vehicle vehicle){
        //
        //Core logic
        // - Find the spot -> if found then generate ticket, else return false

        Spot spot = findAvailableSpot(vehicle.vehicleType);
        if (spot == null) {
            throw new Error("Spot not available");
        }

        try {
            // generate tickets
            Ticket ticket = new Ticket(vehicle, spot);
            tickets.put(ticket.getTicketId(), ticket);
            return ticket;
        } catch (Exception e) {
            // Ticket creation/storage failed
            spot.markAsAvailable();
            throw e;
        }
    }

    private boolean closeTicket(String ticketId){
        // Core logic
        // 1. Validate ticket - invalidId, already closed
        // 1.1 If ticket exist -> capture the exit time
        // 2. Cal fee, free the spot
        // 3. Update ticket state -> CLOSED

        Ticket ticket = fetchTicket(ticketId);
        if(ticket == null){
            throw new Error("Ticket is not invalid");
        }

        // Ticket handles its own concurrency
        Long price = calculatePrice(ticket.entryTime, ticket.updateExitTime(), ticket.getspot().getVehicleType());
        if (!ticket.close(price)) {
            return false;
        }
        ticket.getSpot().markAsAvailable();
        return true;
    }

    private Long calculatePrice(Instant entryTime, Instant exitTime, VehicleType vehicleType){
        long minutes = Duration.between(entryTime, exitTime).toMinutes();
        long billedHours = Math.max(1, ((minutes + 59) / 60 ) * hourlyPrice.get(vehicleType));
        return billedHours;
    }

    private Ticket fetchTicket(String ticketId){
        Ticket ticket = tickets.get(ticketId);
        if(ticket == null){
            return null; // ticket not found
        }

        if(ticket.getStatus() == TicketStatus.CLOSED){
            return null;
        }

        return ticket;
    }

    private Spot findAvailableSpot(VehicleType vehicleType){
        // Core Logic
        // 1. Find the best floor which has max available spots
        // 2. Then find the available spot

        int maxSpot = 0;
        ParkingFloor bestFloor = null;
        for(ParkingFloor floor : floors){
            int spots = floor.getAvailableSpotCount(vehicleType);
            if(maxSpot < spots){
                maxSpot = spots;
                bestFloor = floor;
            }
        }

        if(bestFloor == null){
            return null;
        }
        return bestFloor.findAvailableSpotInFloor(vehicleType);
    }

}


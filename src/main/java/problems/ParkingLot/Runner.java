package problems.ParkingLot;

/*
Primary capability

- When vehicle appears at entry, we will make note of vehicle no, type of vehicle and time.
- Create a ticket for that vehicle and return the slot number, then driver needs to park at that slot
- Mark the slot as booked and ticket as ACTIVE
- At the time of exit we will note the timing and according charge the vehicle.
(Need to calculate the fee based on duration (rounded up to the nearest hour) multiplied by a single configurable rate)
- Mark the slot as free and ticket as CLOSED

Note:
- Types of vehicle - Motorcycle, Car, and Large Vehicle.
- Slot are configurable per type

Error Handling
- If slot is not free, then reject the parking request
- If the ticket is invalid or reused, avoid charging again and freeing the slot
- System needs to handle concurrent requests safely — two vehicles arriving simultaneously should not be assigned the same spot. Handle concurrency
- Similarly for Exit and entry at same time. Handle concurrency

Out of Scope
- How payment is done (card/cash/UPI)
- There is no concept of a driver self-assigning a spot or (parking at wrong slot)
- The system has no mechanism to detect or handle abandoned vehicles
- No UI, physical gates, or cameras, we don't have to design that
 */

public class Runner {

}

//        Entity
//        - ParkingController (class) - orchestrator
//        - Vehicle (class)
//        - Ticket (class)
//        - spot (class)
//        - VehicleType (enum) -  MOTORCYCLE, CAR, LARGE_VEHICLE
//        - SpotStatus (enum) - AVAILABLE, BOOKED
//        - TicketStatus (enum) - CLOSED, ACTIVE
//
//
//        ParkingController:
//         - spotList : List<spot>
//         - floors : List<ParkingFloor>
//         - tickets : Map<ticketId, Ticket> // use new ConcurrentHashMap<>(); for implementation
//         - hourlyPrice : Map<VehicleType, Integer>
//         + findAvailableSpot() : spot
//         + bookTicket() : boolean -> mark spot as BOOKED and ticket as ACTIVE
//         + closeTicket() : boolean -> calculate price, mark spot as AVAILABLE and ticket as CLOSED
//         + calculatePrice() : Long
//
//         ParkingFloor:
//         - floorNumber : Integer
//         - spots : List<spot>
//
//          + getAvailableSpotCount : Integer
//          + findAvailableSpotInFloor : Spot
//
//          Vehicle:
//          - VehicleNumber : String
//          - VehicleType : VehicleType (enum)
//
//        Ticket:
//        - TicketId : String
//        - EntryTime : Instant
//        - ExitTime : Instant?
//        - VehicleId : String
//        - spotId : String
//        - Status : TicketStatus (enum)
//        - Price : Integer?
//        + Ticket() -> constructor
//
//        Spot:
//        - spotId : String
//        - VehicleType : VehicleType (enum)
//        - Status : spotStatus (enum)
//        + markspotAsBooked()
//        + markspotAsAvailable()








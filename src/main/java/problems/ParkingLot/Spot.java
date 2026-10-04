package problems.ParkingLot;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static problems.ParkingLot.spotStatus.BOOKED;

enum VehicleType{
    MOTORCYCLE, CAR, LARGE_VEHICLE
}

enum spotStatus{
    AVAILABLE, BOOKED
}

public class Spot{
// - spotId : String
// - VehicleType : VehicleType (enum)
// - status : spotStatus (enum)

    String spotId;
    VehicleType vehicleType;
    spotStatus status;

    private final Lock lock = new ReentrantLock();
    public boolean tryBook() {
        lock.lock();
        try {
            if (status != spotStatus.AVAILABLE) {
                return false;
            }
            status = BOOKED;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void markAsAvailable() {
        lock.lock();
        try {
            status = spotStatus.AVAILABLE;
        } finally {
            lock.unlock();
        }
    }

    public String getSpotId(){
        return spotId;
    }

    public VehicleType getVehicleType(){
        return vehicleType;
    }

}

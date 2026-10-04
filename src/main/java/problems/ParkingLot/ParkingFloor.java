package problems.ParkingLot;

import java.util.List;

public class ParkingFloor{
//  - floorNumber : Integer
//  - spots : List<spot>
//
//  + getAvailableSpotCount : Integer
//  + findAvailableSpotInFloor : Spot

    int floorNumber;
    List<Spot> spots;

    public int getAvailableSpotCount(VehicleType vehicleType){
        int count = 0;
        for(Spot s : spots){
            if(s.status == spotStatus.AVAILABLE){
                count++;
            }
        }
        return count;
    }
    public Spot findAvailableSpotInFloor(VehicleType vehicleType){
        for(Spot s : spots){
            // find spot which are available and match the vehicleType
            if(s.getVehicleType() == vehicleType){
                if(s.tryBook()){
                    return s;
                }
            }
        }
        return null; // if no spot found
    }
}

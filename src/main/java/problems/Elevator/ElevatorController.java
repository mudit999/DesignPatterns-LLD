package problems.Elevator;
/*
Implementation:
- Define the core logic
- Consider the edge cases
 */

import java.util.ArrayList;
import java.util.List;

import static problems.Elevator.Direction.DOWN;
import static problems.Elevator.Direction.UP;
import static problems.Elevator.RequestType.*;

enum RequestType{
    PICKUP_UP, PICKUP_DOWN, DESTINATION
}

public class ElevatorController {
    List<Elevator> elevatorList;

    public ElevatorController(){
        elevatorList = new ArrayList<>();
    }

    public void step(){
        for(Elevator e : elevatorList){
            e.step();
        }
    }
    public Elevator requestElevator(int floor, RequestType type){
        /*
        Core logic:
        1. Find the best elevator to handle this request
        2. Send the request to elevator

        Edge case:
        Floor out of bound -> throw error
         */

        if (floor < 0 || floor > 9){
            throw new Error("Floor is invalid");
        }

        Request request = new Request(floor, type);
        Elevator best = selectBestElevator(request);
        return best.addRequests(request);
    }

    private Elevator selectBestElevator(Request request){
        /*
        Core logic
        1. Find the elevator moving towards
        2. If none found, try idle elevator (nearest)
        3. Pick the nearest
         */
        Elevator best = findMovingTowards(request);
        if(best != null){
            return best;
        }

        best = findNearestIdle(request.getFloor());
        if(best != null){
            return best;
        }

        return findNearest(request.getFloor());
    }

//    private Elevator findNearest(int floor) {
//    }
//
//    private Elevator findNearestIdle(int floor) {
//    }

    private Elevator findMovingTowards(Request request){
        /*
        Core logic
        1. Scan the elevator going that direction
        2. Keep track of closest
        3. return closest
        */

        if(request.type == DESTINATION){
            throw new Error("Request type is not correct");
        }

        int rfloor = request.getFloor();
        Elevator nearest = null;
        int minDistance = Integer.MAX_VALUE;
        Direction reqDir = request.getType() == PICKUP_UP ? UP : DOWN;

        for(Elevator e : elevatorList){
            if(e.direction != reqDir){
                continue;
            }
            if(e.direction == UP && e.floor > rfloor || e.direction == DOWN && e.floor < rfloor){
                continue;
            }

            int distance = Math.abs(e.floor - rfloor);
            if(minDistance > distance){
                distance = minDistance;
                nearest = e;
            }
        }
        return nearest;
    }
}

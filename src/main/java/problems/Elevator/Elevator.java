package problems.Elevator;

import java.util.Set;

import static problems.Elevator.Direction.*;
import static problems.Elevator.RequestType.*;

enum Direction{
    UP, DOWN, IDLE
}

public class Elevator {
    int floor;
    Direction direction;
    Set<Request> requestSet;

    public void step(){
        /*
        Core logic
        1. If idle with request, pick direction towards the nearest request
        2. Check if we should stop of the current floor (matches hall call or destination)
        3. If no request ahead of us (and other pending request), reverse
        4. else (1,2,3 does not apply), then Move on the floor in current direction

        Edge cases
        1. no request -> set IDLE, and return
        2. Stop, move can't happen in same tick, similarly reverse and move
         */

        // Case : Nothing to do, no request
        if(requestSet.isEmpty()){
            direction = IDLE;
            return;
        }

        // Case: Pick direction if idle
        if(direction == IDLE){
            Request nearest = findNearestRequest();
            direction = nearest.getFloor() > floor ? UP : DOWN;
            return;
        }

        // Case: Stop at current floor?
        RequestType type = direction == UP ? PICKUP_UP : PICKUP_DOWN;
        Request hallCallRequest = new Request(floor, type);
        Request destinationRequest = new Request(floor, DESTINATION);

        if(requestSet.contains(hallCallRequest) || requestSet.contains(destinationRequest)){
            requestSet.remove(hallCallRequest);
            requestSet.remove(destinationRequest);
            stop(); // lift door open
            return;
        }

        // Case: Reverse if nothing ahead
        if(!hasRequestAhead(direction)){
            direction = direction == UP ? DOWN : UP;
        }

        // Case: Move
        if(direction == UP){
            floor++;
        }else if(direction == DOWN){
            floor--;
        }

    }

    private void stop(){
        // just stop the elevator
    }

    private Request findNearestRequest(){
        // scan all request, find the closest by floor distance
        Request nearest = null;
        int minDist = Integer.MAX_VALUE;

        for(Request req : requestSet){
            int dist = Math.abs(req.floor - floor);
            if(minDist > dist){
                minDist = dist;
                nearest = req;
            }
        }
        return nearest;
    }

    private boolean hasRequestAhead(Direction dir){
        for(Request req : requestSet){
            if(dir == UP && req.getFloor() > floor){
                return true;
            }

            if(dir == DOWN && req.getFloor() < floor) {
                return true;
            }
        }
        return false;

    }
    public Elevator addRequests(Request request){
        requestSet.add(request);
        return this;
    }
}

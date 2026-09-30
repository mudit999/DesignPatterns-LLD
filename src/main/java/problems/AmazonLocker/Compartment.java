package problems.AmazonLocker;

enum sizes{
        SMALL, MEDIUM, LARGE
}

enum compartmentStatus{
    AVAILABLE,
    OCCUPIED,
    OUT_OF_MAINTENANCE
}

public class Compartment {
    sizes size;
    compartmentStatus status;

    public boolean isOccupied(){
        if(status == compartmentStatus.OCCUPIED){
            return true;
        }
        return false;
    }
    public void markOccupied(){
        status = compartmentStatus.OCCUPIED;
    }

    public void markFree(){
        status = compartmentStatus.AVAILABLE;
    }

    public void open(){
        System.out.println("compartment is open now");
    }

    public sizes getSize(){
        return size;
    }
}

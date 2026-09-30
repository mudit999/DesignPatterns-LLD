package problems.AmazonLocker;
import java.time.Instant;

public class AccessToken {
    String code;
    Instant expiration;
    Compartment compartment;

    public AccessToken(String code, Instant expiration, Compartment compartment) {
        this.code = code;
        this.expiration = expiration;
        this.compartment = compartment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isExpired(){
        Instant currentTime = Instant.now();
        if(currentTime.isAfter(expiration)){
            return true;
        }
        return false;
    }

    public Compartment getCompartment() {
        return compartment;
    }
}

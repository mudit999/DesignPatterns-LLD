package problems.AmazonLocker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class Locker {
    private List<Compartment> compartments;
    private Map<String, AccessToken> accessTokenMapping;

    public String depositPackage(String size) throws Exception {
        Compartment compartment = getAvailableCompartment(size);
        if(compartment == null){
            throw new Exception("No compartment is available of specified size");
        }
        compartment.open();
        compartment.markOccupied();
        AccessToken accessToken = generateAccessToken(compartment);
        String code = accessToken.getCode();
        accessTokenMapping.put(code, accessToken);
        return code;
    }

    private Compartment getAvailableCompartment(String size){
        /*
        1. scan all compartment
        2. traverse each compartment -> if free and correct size
        3. first match and return it
         */

        for(Compartment c: compartments){
            if(c.getSize().equals(size) && !c.isOccupied()) {
                return c;
            }
        }

        return null;
    }

    private AccessToken generateAccessToken(Compartment compartment){
        final Random random = new Random();
        String code = String.format("%06d", random.nextInt(1_000_000));
        Instant expiration = Instant.now().plus(7, ChronoUnit.DAYS);
        return new AccessToken(code, expiration, compartment);
    }

    public void pickup(String code) throws Exception {
        /*
        Core logic
        1. Look up the code to get accessToken
        2. get the compartment
        3. open the compartment
        4. mark the compartment as free
        5. Remove accessCode from the map

        Edge cases:
        1. validation of the code itself, not empty -> throw
        2. Code is not mapping in accessTokenMapping -> throw
        3. accessToken is expired
         */

        if(code.isEmpty()){
            throw new Exception("code is emppty");
        }
        boolean accessTokenFound = accessTokenMapping.containsKey(code);

        if(!accessTokenFound){
            throw new Exception("Invalid code");
        }

        AccessToken accessToken = accessTokenMapping.get(code);

        if(accessToken.isExpired()){
            throw new Exception("Access token is expired");
        }

        Compartment compartment = accessToken.getCompartment();
        compartment.open();
        compartment.markFree();
        accessTokenMapping.remove(code);

    }

    public void openExpiredCompartments(){
        /*
        Core logic
        1. Scan all access token in mapping
        2. find the expired token
        3. get the compartment
        4. Open the compartment
        ------- staff will remove the stuff
        5. Mark the compartment as available again
        6. Remove access code from map ? Ask interviewer -> for now -> No
         */

        for(Map.Entry<String, AccessToken> entry: accessTokenMapping.entrySet()){
            if(entry.getValue().isExpired()){ // value -> accessToken
                Compartment compartment = entry.getValue().getCompartment();
                    compartment.open();
                    compartment.markFree();
            }
        }
    }

    // Extension of code
    private Compartment getAvailableCompartmentExtension(String size){
        List<sizes> sizeInOrder = List.of(
                sizes.SMALL,
                sizes.MEDIUM,
                sizes.LARGE);

        int getRequestedSizeIndex = sizeInOrder.indexOf(size);

        for(int i=getRequestedSizeIndex; i<=sizeInOrder.size()-1; i++) {
            sizes sizeC = sizeInOrder.get(i);
            for (Compartment c : compartments) {
                if (c.getSize().equals(sizeC) && !c.isOccupied()) {
                    return c;
                }
            }
        }

        return null;
    }

    // How that delivery guy actually put the package
    // 2-phased-commit
}

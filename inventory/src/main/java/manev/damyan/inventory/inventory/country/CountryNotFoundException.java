package manev.damyan.inventory.inventory.country;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String iso){
        super("Could not find country with iso: " + iso);
    }
}

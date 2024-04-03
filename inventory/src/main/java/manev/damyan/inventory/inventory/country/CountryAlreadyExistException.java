package manev.damyan.inventory.inventory.country;

public class CountryAlreadyExistException extends RuntimeException {

    public CountryAlreadyExistException(String iso) {
        super(String.format("Country with iso %s already exists", iso));
    }
}

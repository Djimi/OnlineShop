package manev.damyan.inventory.inventory.country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    public List<CountryDTO> getAllCountries();

    public Optional<CountryDTO> getSpecificCountry(String iso);

    public CountryDTO addNewCountry(CountryDTO country);

    public void deleteCountry(String iso);
}

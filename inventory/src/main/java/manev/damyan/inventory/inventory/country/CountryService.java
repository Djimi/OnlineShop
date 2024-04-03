package manev.damyan.inventory.inventory.country;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository repository;

    private final CountryMapper countryMapper;

    public CountryService(CountryRepository repository, CountryMapper countryMapper) {
        this.repository = repository;
        this.countryMapper = countryMapper;
    }

    public List<CountryDTO> getAllCountries() {
        return repository.findAll().stream()
                .map(countryMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CountryDTO> getSpecificCountry(String iso) {
        return repository.findById(iso).map(countryMapper::convertToDTO);
    }

    public CountryDTO addNewCountry(CountryDTO country) {
        if (repository.findById(country.getIso()).isPresent()) {
            throw new CountryAlreadyExistException(country.getIso());
        } else {
            Country entity = countryMapper.convertToEntity(country);
            Country savedCountry = repository.save(entity);

            return countryMapper.convertToDTO(savedCountry);
        }
    }

    public void deleteCountry(String iso) {
        repository.findById(iso).orElseThrow(() -> new CountryNotFoundException(iso));

        repository.deleteById(iso);
    }
}

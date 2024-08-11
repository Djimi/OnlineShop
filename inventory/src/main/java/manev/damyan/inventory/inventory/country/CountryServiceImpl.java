package manev.damyan.inventory.inventory.country;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository repository, CountryMapper countryMapper) {
        this.repository = repository;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<CountryDTO> getAllCountries() {
        return repository.findAll().stream()
                .map(country -> countryMapper.convertToDTO(country, new CountryDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CountryDTO> getSpecificCountry(String iso) {
        return repository.findById(iso).map(country -> countryMapper.convertToDTO(country, new CountryDTO()));
    }

    @Override
    public CountryDTO addNewCountry(CountryDTO country) {
        if (repository.findById(country.getIso()).isPresent()) {
            throw new CountryAlreadyExistException(country.getIso());
        } else {
            Country entity = countryMapper.convertToEntity(country, new Country());
            Country savedCountry = repository.save(entity);

            return countryMapper.convertToDTO(savedCountry, new CountryDTO());
        }
    }


    @Override
    public void deleteCountry(String iso) {
        repository.findById(iso).orElseThrow(() -> new CountryNotFoundException(iso));

        repository.deleteById(iso);
    }
}

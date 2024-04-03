package manev.damyan.inventory.inventory.country;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {

        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.OK);
    }

    @GetMapping("{iso}")
    public ResponseEntity<CountryDTO> getSpecifyCountry(@PathVariable String iso) throws CountryNotFoundException {
        return new ResponseEntity<>(countryService.getSpecificCountry(iso)
                .orElseThrow(() -> new CountryNotFoundException(iso)), HttpStatus.OK);
    }

    @PutMapping("{iso}")
    public ResponseEntity<CountryDTO> createOrReplaceCountry(@RequestBody CountryDTO country, @PathVariable String iso) {
        return new ResponseEntity<>(countryService.addNewCountry(country), HttpStatus.CREATED);
    }

    @DeleteMapping("{iso}")
    public ResponseEntity<?> deleteCountry(@PathVariable String iso) {

        countryService.deleteCountry(iso);
        return ResponseEntity.noContent().build();
    }

}

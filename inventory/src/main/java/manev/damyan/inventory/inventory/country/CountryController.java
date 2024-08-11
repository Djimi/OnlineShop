package manev.damyan.inventory.inventory.country;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/countries", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class CountryController {

    private final CountryServiceImpl countryService;

    public CountryController(CountryServiceImpl countryService) {

        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.OK);
    }

    @GetMapping("{iso}")
    public ResponseEntity<CountryDTO> getSpecifyCountry(@CountryNameConstraint @PathVariable String iso) throws CountryNotFoundException {
        return new ResponseEntity<>(countryService.getSpecificCountry(iso)
                .orElseThrow(() -> new CountryNotFoundException(iso)), HttpStatus.OK);
    }

    @PutMapping("{iso}")
    public ResponseEntity<CountryDTO> createOrReplaceCountry(@Validated @RequestBody CountryDTO country, @CountryNameConstraint @PathVariable String iso) {
        return new ResponseEntity<>(countryService.addNewCountry(country), HttpStatus.CREATED);
    }

    @DeleteMapping("{iso}")
    public ResponseEntity<?> deleteCountry(@CountryNameConstraint @PathVariable String iso) {

        countryService.deleteCountry(iso);
        return ResponseEntity.noContent().build();
    }

}

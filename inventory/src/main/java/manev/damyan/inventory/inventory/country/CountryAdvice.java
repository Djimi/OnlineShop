package manev.damyan.inventory.inventory.country;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CountryAdvice {

    @ResponseBody
    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String countryNotFoundException(CountryNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CountryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String countryNotFoundException(CountryAlreadyExistException ex) {
        return ex.getMessage();
    }

}

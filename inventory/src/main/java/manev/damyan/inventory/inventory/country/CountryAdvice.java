package manev.damyan.inventory.inventory.country;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import manev.damyan.inventory.inventory.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class CountryAdvice {

    @ResponseBody
    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse countryNotFoundException(CountryNotFoundException ex, WebRequest webRequest) {
        log.info("Country is not found", ex);
        return new ErrorResponse(UUID.randomUUID().toString(), webRequest.getContextPath(), "Country is not existing", null);
    }

    @ResponseBody
    @ExceptionHandler(CountryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse countryAlreadyExistsException(CountryAlreadyExistException ex, WebRequest webRequest) {
        log.info("Country already exists!", ex);
        return new ErrorResponse(UUID.randomUUID().toString(), webRequest.getContextPath(), "Country is not existing", null);
    }

}

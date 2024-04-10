package manev.damyan.inventory.inventory.inventory;

import manev.damyan.inventory.inventory.country.CountryNotFoundException;
import manev.damyan.inventory.inventory.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class InventoryAdvice {
    @ResponseBody
    @ExceptionHandler(InsufficientResourceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ErrorResponse countryNotFoundException(InsufficientResourceException ex) {
        return new ErrorResponse(UUID.randomUUID().toString(), ex.getMessage(), null);
    }

}

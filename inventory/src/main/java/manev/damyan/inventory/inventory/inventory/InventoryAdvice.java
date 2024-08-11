package manev.damyan.inventory.inventory.inventory;

import lombok.extern.slf4j.Slf4j;
import manev.damyan.inventory.inventory.country.CountryNotFoundException;
import manev.damyan.inventory.inventory.exception.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.UUID;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InventoryAdvice {
    @ResponseBody
    @ExceptionHandler(InsufficientResourceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ErrorResponse countryNotFoundException(InsufficientResourceException ex, WebRequest webRequest) {
        log.info("Not enough inventory to purchase!", ex);
        return new ErrorResponse(UUID.randomUUID().toString(), webRequest.getContextPath(), ex.getMessage(), null);
    }

}

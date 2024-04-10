package manev.damyan.purchase.exception;

import manev.damyan.purchase.purchases.IncorrectOrderDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class ControllerAdviceMapper {

    @ResponseBody
    @ExceptionHandler(IncorrectOrderDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse warehouseNotFoundException(IncorrectOrderDataException ex) {
        return new ErrorResponse(UUID.randomUUID().toString(), ex.getMessage(), null);
    }
}

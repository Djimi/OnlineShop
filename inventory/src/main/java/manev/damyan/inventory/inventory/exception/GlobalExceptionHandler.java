package manev.damyan.inventory.inventory.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNotPredictedException(Exception e, WebRequest request) {

        log.debug("Exception occured!");
        e.printStackTrace();

        HashMap<String, Object> additionalInformation = new HashMap<>();

        List<String> tags = Arrays.asList(AdditionalProperties.Tags.Values.UNHANDLED_ERRORS);
        additionalInformation.put(AdditionalProperties.Tags.KEY, tags);

        return new ErrorResponse(UUID.randomUUID().toString(),
                request.getContextPath(),
                "Not handled exception is thrown! Please contact the support!" + e.getMessage(),
                additionalInformation);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintException(ConstraintViolationException e, WebRequest request) {

        log.debug("Constraint validation occurred!");
        e.printStackTrace();

        HashMap<String, Object> additionalInformation = new HashMap<>();

        List<String> validationErrors = e.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath().toString() + " : " + cv.getMessage()).collect(Collectors.toList());

        List<String> tags = Arrays.asList(AdditionalProperties.Tags.Values.INPUT_VALIDATION_ERRORS);
        additionalInformation.put(AdditionalProperties.Tags.KEY, tags);

        additionalInformation.put(AdditionalProperties.VALIDATION_ERRORS, validationErrors);

        return new ErrorResponse(UUID.randomUUID().toString(),
                request.getContextPath(),
                "Exception while validation input parameters!",
                additionalInformation);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {

        log.debug("Argument not valid exception occurred!");
        e.printStackTrace();

        HashMap<String, Object> additionalInformation = new HashMap<>();

        List<String> validationErrors = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + " : " + fe.getDefaultMessage()).collect(Collectors.toList());

        List<String> tags = Arrays.asList(AdditionalProperties.Tags.Values.INPUT_VALIDATION_ERRORS);
        additionalInformation.put(AdditionalProperties.Tags.KEY, tags);

        additionalInformation.put(AdditionalProperties.VALIDATION_ERRORS, validationErrors);

        return new ErrorResponse(UUID.randomUUID().toString(),
                request.getContextPath(),
                "Exception while validation input parameters!",
                additionalInformation);
    }

    @ResponseBody
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleMethodArgumentNotValidException(NoResourceFoundException e, WebRequest request)
            throws NoResourceFoundException {

        log.debug("NoResourceFoundException exception occurred!");
    }

}

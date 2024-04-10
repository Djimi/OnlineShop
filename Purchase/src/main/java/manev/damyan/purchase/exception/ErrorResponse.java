package manev.damyan.purchase.exception;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {

    private final String requestId;

    private final String message;

    private final Map<String, Object> additionalInformation;
}

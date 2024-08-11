package manev.damyan.inventory.inventory.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;


@Schema(
        name = "Error response",
        description = "Information about the error which has occurred!"
)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final String requestId;

    private final String path;

    private final String errorMessage;

    private final Map<String, Object> additionalInformation;
}

package manev.damyan.inventory.inventory.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseDTO {

    private long id;

    @NotNull
    private String country_iso;

    @NotNull
    private String name;

    @NotNull
    private String city;

    @NotNull
    @JsonProperty("address_of_warehouse")
    private String localAddress;
}

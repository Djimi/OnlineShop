package manev.damyan.inventory.inventory.items;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(name = "Item", description = "This is schema for item!")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    @Schema(description = "Id of the item", example = "3")
    private Long id;

    @Schema(description = "Verbose name of the item. It is user friendly", example = "Pencil")
    private String name;

    @Schema(description = "The type of the item", example = "laptop")
    private String type;

    @Schema(description = "Description of the item which explains clearly what the item is", example = "New, super nice pencil, which you will love to use!")
    private String description;
}

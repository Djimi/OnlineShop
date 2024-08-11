package manev.damyan.inventory.inventory.items;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import manev.damyan.inventory.inventory.exception.ErrorResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Items",
        description = "Enpoints for updating Items metainformation in inventory"
)
@RestController
@RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
/* this annotation is needed, because of getItem(long), because hibernate validator
will validate objects which has annotations inside for their properties, but String
is not such, so it will not be validated if that annotation is missing
In practice if yoy miss this validation still the id will be validated, but
different exception will be thrown
 */
public class ItemController {

    private ItemsService itemsService;

    public ItemController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable(name = "id") @Min(1) long id) {

        Optional<ItemDTO> dto = itemsService.getItem(id);

        if (dto.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(dto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @Operation(
            summary = "Create Item REST API",
            description = "This is simple create API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "New item is created!"
            ),
            // the bellow one is just example. Our endpoint doesn't really returns such code
            @ApiResponse(
                    responseCode = "402",
                    description = "Missing ID",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> createItem(@RequestBody @Valid ItemDTO dto) {
        if (dto.getId() != null) {
            /* that's bad practise as if the item is invalid due to another
             reason, we will return different response, so we have cases
             where you return the same response code with different body
             which is weird and confusing.
            */
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(itemsService.create(dto));
    }

    @GetMapping(params = "nameCaseInsensitive")
    public ResponseEntity<Optional<List<ItemDTO>>> getItemsByNameCaseInsensitive(
            @RequestParam(name = "nameCaseInsensitive") String nameInsensitive) {
        return ResponseEntity.status(HttpStatus.OK).body(itemsService.getAllByNameInsensitive(nameInsensitive));
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemsService.getAllItems());
    }

    @GetMapping(params = "name")
    public ResponseEntity<Optional<List<Item>>> getItemsByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(itemsService.getAllByName(name));
    }

    @GetMapping(params = { "name", "id" })
    public ResponseEntity<List<ItemDTO>> getItemsWithShortDescription(@RequestParam(name = "name") String name,
            @RequestParam("id") Long id, @RequestParam(value = "short", required = true)
    String shortParam) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemsService.getAllItemsByNameWithShortRepresentation(name, id));
    }

    @GetMapping(value = "/paginatedSearch", params = { "page", "size", "sortBy" })
    public ResponseEntity<List<ItemDTO>> getItemsPaginated(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "sortBy") String sortBy) {

        Page<ItemDTO> paginated = itemsService.getPaginated(page, size, sortBy);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Page-size", String.valueOf(paginated.getSize()))
                .header("Page-number", String.valueOf(paginated.getNumber()))
                .header("Page-counts", String.valueOf(paginated.getTotalPages()))
                .header("Page-number-of-elements", String.valueOf(paginated.getTotalElements()))
                .body(paginated.stream().toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(name = "id") long id) {

        if (itemsService.deleteItem(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(params = "name")
    public ResponseEntity<?> deleteItemByName(@RequestParam(name = "name") String name) {

        List<ItemDTO> deletedItems = itemsService.deleteByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deletedItems);
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable(name = "id") long id, @RequestBody @Valid ItemDTO dto) {

        boolean created = itemsService.update(dto);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    @GetMapping("/fake")
    public ResponseEntity<ItemDTO> fakeEnpointWhichThrowsInternalError() {
        return ResponseEntity.of(itemsService.fake());
    }
}

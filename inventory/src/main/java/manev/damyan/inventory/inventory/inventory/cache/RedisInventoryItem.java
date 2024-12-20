package manev.damyan.inventory.inventory.inventory.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("InventoryItem")
public class RedisInventoryItem {

    private Long id;

    @Indexed
    private String name;

    private String type;

    private String detailedDescription;
}

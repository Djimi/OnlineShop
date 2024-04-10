package manev.damyan.purchase.purchases;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Purchase {

    @Id
    private String id;

    private String profileId;
    private List<PurchaseItem> purchaseItems;

    private String address;
}

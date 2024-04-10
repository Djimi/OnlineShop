package manev.damyan.purchase.purchases;

import lombok.Data;

import java.util.List;

@Data
public class CreatePurchaseDTO {

    private String profileId;
    private List<PurchaseItemDTO> purchaseItems;

    private String address;
}

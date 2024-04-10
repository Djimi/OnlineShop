package manev.damyan.purchase.purchases;

import lombok.AllArgsConstructor;
import manev.damyan.purchase.inventory.InventoryService;
import manev.damyan.purchase.profile.ProfileDTO;
import manev.damyan.purchase.profile.ProfileService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PurchaseService {

    private PurchaseRepository purchaseRepository;

    private PurchaseMapper purchaseMapper;

    private InventoryService inventoryService;

    private ProfileService profileService;

    public PurchaseDTO createPurchase(CreatePurchaseDTO dto) {

        Mono<ProfileDTO> profile = profileService.getProfile(dto.getProfileId());

        List<Mono<PurchaseItemDTO>> updatingInventories = dto.getPurchaseItems().stream()
                .map(purchaseItem -> inventoryService.reduceInventory(purchaseMapper.convertToEntity(purchaseItem)).map(pi -> purchaseMapper.convertToDTO(pi))).collect(Collectors.toList());

        Mono.when(updatingInventories).and(profile).block();

        Purchase purchase = purchaseRepository.save(purchaseMapper.convertToEntity(dto));

        return purchaseMapper.convertToDTO(purchase);
    }

    public Optional<PurchaseDTO> getPurchase(String id) {
        return purchaseRepository.findById(id).map(purchaseMapper::convertToDTO);
    }

    public List<PurchaseDTO> getAllPurchases() {
        return purchaseMapper.convertListToDTO(purchaseRepository.findAll());
    }

    public PurchaseDTO updatePurchase(PurchaseDTO dto) {
        Purchase entity = purchaseMapper.convertToEntity(dto);
        entity.setPurchaseItems(purchaseRepository.findById(dto.getId()).orElseThrow().getPurchaseItems());
        return purchaseMapper.convertToDTO(purchaseRepository.save(entity));
    }

    public boolean deletePurchase(String id) {
        if (!purchaseRepository.findById(id).isPresent()) {
            return false;
        } else {
            purchaseRepository.deleteById(id);
            return true;
        }
    }
}

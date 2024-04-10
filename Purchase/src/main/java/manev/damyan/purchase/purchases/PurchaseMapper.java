package manev.damyan.purchase.purchases;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseMapper {

    List<PurchaseDTO> convertListToDTO(List<Purchase> dtos);

    @Mapping(target = "id", ignore = true)
    Purchase convertToEntity(CreatePurchaseDTO dto);


    @Mapping(target = ".", source = "purchase")
    Purchase convertToEntity(PurchaseDTO dto);
    List<PurchaseItem> convertToEntity(List<PurchaseItemDTO> dtos);

    PurchaseItem convertToEntity(PurchaseItemDTO dto);

    @Mapping(target = "purchase", source = "entity")
    PurchaseDTO convertToDTO(Purchase entity);

    List<PurchaseItemDTO> convertToDTO(List<PurchaseItem> entities);

    PurchaseItemDTO convertToDTO(PurchaseItem entity);
}

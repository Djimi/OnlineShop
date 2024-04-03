package manev.damyan.inventory.inventory.items;

import ch.qos.logback.core.model.ComponentModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    @InheritInverseConfiguration
    Item convertToEntity(ItemDTO dto);

    @Mapping(source = "detailedDescription", target = "description")
    ItemDTO convertToDTO(Item entity);
}

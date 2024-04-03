package manev.damyan.inventory.inventory.country;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {
    Country convertToEntity(CountryDTO dto);

    CountryDTO convertToDTO(Country country);
}

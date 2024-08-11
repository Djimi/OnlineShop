package manev.damyan.inventory.inventory.country;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {
    Country convertToEntity(CountryDTO dto, @MappingTarget Country country);

    CountryDTO convertToDTO(Country country, @MappingTarget CountryDTO countryDTO);
}

package manev.damyan.inventory.inventory.warehouse;

import manev.damyan.inventory.inventory.country.Country;
import manev.damyan.inventory.inventory.country.CountryDTO;
import manev.damyan.inventory.inventory.country.CountryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WarehouseMapper {

    @Autowired
    protected CountryRepository countryRepository;

    @Mapping(target = "address", expression = "java(String.format(\"%s|%s\", dto.getCity(), dto.getLocalAddress()))")
    @Mapping(target = "country", source = "country_iso", qualifiedByName = "mapCountry")
    @Mapping(target = "inventories", ignore = true)
    public abstract Warehouse convertToEntity(WarehouseDTO dto);

    @Mapping(target = "city", expression = "java(entity.getAddress().substring(0, entity.getAddress().indexOf(\"|\")))")
    @Mapping(target = "localAddress", expression = "java(entity.getAddress().substring(entity.getAddress().indexOf(\"|\") + 1))")
    @Mapping(target = "country_iso", expression = "java(entity.getCountry().getIso())")
    public abstract WarehouseDTO convertToDTO(Warehouse entity);

    @Named("mapCountry")
    protected Country mapCountry(String countryIso){
        return countryRepository.getReferenceById(countryIso);
    }
}

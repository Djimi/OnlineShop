package manev.damyan.accounts.profile;

import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProfileMapper {

    @ProfileDTOToEntityMapping
    Profile convertDTOToEntity(ProfileDTO dto);

    @ProfileDTOToEntityMapping
    @Mapping(target = "id", ignore = true)
    Profile convertNewProfileDTOToEntity(NewProfileDTO newProfileDTO);

    @InheritInverseConfiguration
    ProfileDTO convertEntityToDTO(Profile entity);
}

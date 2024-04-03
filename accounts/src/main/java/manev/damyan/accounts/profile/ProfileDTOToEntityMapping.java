package manev.damyan.accounts.profile;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(source = "username",target = "loginName")
@Mapping(source = "description", target = "userDescription")
public @interface ProfileDTOToEntityMapping {
}

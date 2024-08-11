package manev.damyan.inventory.inventory.country;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotEmpty(message = "iso of the country should not be empty!")
@Size(min = 3, max = 3, message = "iso should contain exactly 3 symbols")
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface CountryNameConstraint {
    String message() default "Country validation failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package bg.softuni.restaurants_management.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueRestaurantNameValidator.class)
public @interface UniqueRestaurantName {
    String message() default "The restaurant name should be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

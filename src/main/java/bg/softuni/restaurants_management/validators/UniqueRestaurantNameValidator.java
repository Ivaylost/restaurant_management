package bg.softuni.restaurants_management.validators;

import bg.softuni.restaurants_management.repository.RestaurantRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRestaurantNameValidator implements ConstraintValidator<UniqueRestaurantName, String> {
    private final RestaurantRepository restaurantRepository;

    public UniqueRestaurantNameValidator(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return restaurantRepository
                .findByName(value)
                .isEmpty();
    }
}

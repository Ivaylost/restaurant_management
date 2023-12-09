package bg.softuni.restaurants_management.validators;

import bg.softuni.restaurants_management.repository.TableRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueTableNameValidator implements ConstraintValidator<UniqueTableName, String> {

    private final TableRepository tableRepository;

    public UniqueTableNameValidator(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }

}

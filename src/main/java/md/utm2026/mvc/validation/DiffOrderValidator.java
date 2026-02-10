package md.utm2026.mvc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class DiffOrderValidator implements ConstraintValidator<DiffOrder, Object[]> {

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
        if (value == null || value.length < 2) {
            return true;
        }
        Object first = value[0];
        Object second = value[1];
        if (!(first instanceof Number) || !(second instanceof Number)) {
            return true;
        }
        return ((Number) first).longValue() > ((Number) second).longValue();
    }
}

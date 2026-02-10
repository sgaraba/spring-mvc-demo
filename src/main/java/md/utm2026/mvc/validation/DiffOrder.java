package md.utm2026.mvc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DiffOrderValidator.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DiffOrder {

    String message() default "p1 trebuie sa fie mai mare decat p2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package sn.graim.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Pattern(regexp=".+@.+\\..+", message="{ExtendedEmailValidator.email}")
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ExtendedEmailValidator {
	String message() default "{ExtendedEmailValidator.email}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

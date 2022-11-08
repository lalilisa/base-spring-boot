package com.n10.webbook.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = CCCDValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CCCD{
}


class CCCDValidator implements ConstraintValidator<CCCD, String> {
    @Override
    public boolean isValid(String cccd, ConstraintValidatorContext constraintValidatorContext) {
            if(cccd.length()==12){
                if (cccd.matches("^\\d+$")) {
                    return true;
                }
            }
            return false;
    }
}

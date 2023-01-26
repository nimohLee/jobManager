package com.nimoh.jobManager.validator;


import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIdValidator.class)
public @interface UserId {
    String message() default "id";
    Class[] groups() default {};
    Class[] payload() default {};
}

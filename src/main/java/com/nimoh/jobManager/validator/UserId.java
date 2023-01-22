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
    String message() default "영문자, 숫자를 조합하여 6자 이상 1자 이하로 아이디를 입력해주세요";
    Class[] groups() default {};
    Class[] payload() default {};
}

package com.nimoh.hotel.validator;


import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "영문자, 숫자를 조합하여 8자 이상 12자 이하로 비밀번호를 입력해주세요";
    Class[] groups() default {};
    Class[] payload() default {};
}

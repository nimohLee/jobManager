package com.nimoh.jobManager.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 패스워드 유효성 검사
 *
 * @author nimoh
 */

public class PasswordValidator implements ConstraintValidator<Password, String> {

    /**
     * 비밀번호 유효성 검사
     *
     * @param value 유효성을 검사할 비밀번호
     * @param context context in which the constraint is evaluated
     *
     * @return 유효한 경우 true 반환
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,12}$");
    }
}

package com.nimoh.jobManager.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 유저 아이디 유효성 검사
 *
 * @author nimoh
 */
public class UserIdValidator implements ConstraintValidator<UserId, String> {

    /**
     * 아이디 유효성 검사
     *
     * @param value 유효성 검사할 유저 아이디
     * @param context context in which the constraint is evaluated
     *
     * @return 유효한 아이디인 경우 true 반환
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$");
    }
}

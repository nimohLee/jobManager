package com.nimoh.jobManager.data.dto.user;

import com.nimoh.jobManager.validator.Password;
import com.nimoh.jobManager.validator.UserId;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 회원 가입 요청 DTO
 *
 * @author nimoh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserSignUpRequest {

    @UserId
    String uid;

    @NotBlank
    String name;

    @Email
    String email;

    @Password
    String password;
}

package com.nimoh.jobManager.data.dto.user;

import com.nimoh.jobManager.validator.Password;
import com.nimoh.jobManager.validator.UserId;
import lombok.*;

/**
 * 로그인 요청 DTO
 *
 * @author nimoh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserLogInRequest {

    @UserId
    private String uid;

    @Password
    private String password;

}

package com.nimoh.jobManager.data.dto.user;

import com.nimoh.jobManager.validator.Password;
import com.nimoh.jobManager.validator.UserId;
import lombok.*;

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

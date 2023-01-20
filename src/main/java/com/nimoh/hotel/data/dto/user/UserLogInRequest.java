package com.nimoh.hotel.data.dto.user;

import com.nimoh.hotel.validator.Password;
import com.nimoh.hotel.validator.UserId;
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

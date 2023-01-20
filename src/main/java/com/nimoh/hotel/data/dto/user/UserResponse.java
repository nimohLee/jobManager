package com.nimoh.hotel.data.dto.user;

import com.nimoh.hotel.validator.Password;
import com.nimoh.hotel.validator.UserId;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserResponse {
    private Long id;
    private String uid;
    private String name;
    private String email;
}

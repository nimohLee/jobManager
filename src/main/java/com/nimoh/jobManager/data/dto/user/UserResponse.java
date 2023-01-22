package com.nimoh.jobManager.data.dto.user;

import lombok.*;

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

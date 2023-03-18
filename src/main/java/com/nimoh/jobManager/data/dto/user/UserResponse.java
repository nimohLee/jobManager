package com.nimoh.jobManager.data.dto.user;

import lombok.*;

/**
 * 회원 가입과 로그인 성공 시 반환하는 DTO
 *
 * @author nimoh
 */
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

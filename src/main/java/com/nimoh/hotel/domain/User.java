package com.nimoh.hotel.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String email;
}

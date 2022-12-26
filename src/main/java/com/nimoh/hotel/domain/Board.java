package com.nimoh.hotel.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Board {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private String category;
    private Date regDate;
}

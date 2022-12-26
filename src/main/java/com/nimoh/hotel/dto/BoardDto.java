package com.nimoh.hotel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BoardDto {
    private String title;
    private String writer;
    private String content;
    private String category;
}

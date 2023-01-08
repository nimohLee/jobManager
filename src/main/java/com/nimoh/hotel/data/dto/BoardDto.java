package com.nimoh.hotel.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Builder
public class BoardDto {
    private String title;
    private String writer;
    private String content;
    private String category;
}

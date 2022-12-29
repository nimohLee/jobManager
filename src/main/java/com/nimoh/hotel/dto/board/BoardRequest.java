package com.nimoh.hotel.dto.board;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRequest {
    private String title;
    private String content;
    private String writer;
    private String category;
}

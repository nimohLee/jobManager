package com.nimoh.jobManager.data.dto.board;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRequest {

    private String title;
    private String content;
    private Long writer;
    private String category;
}

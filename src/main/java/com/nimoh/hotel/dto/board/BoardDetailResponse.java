package com.nimoh.hotel.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
@Builder
public class BoardDetailResponse {
    private final Long id;
    private final String title;
    private final String writer;
    private final String content;
    private final String category;
    private final Date regDate;
}

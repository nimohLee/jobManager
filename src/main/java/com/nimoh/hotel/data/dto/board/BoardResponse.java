package com.nimoh.hotel.data.dto.board;

import com.nimoh.hotel.data.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@Builder
public class BoardResponse {
    private final Long id;
    private final String title;
    private final User user;
    private final String content;
    private final String category;
    private final LocalDateTime regDate;
}
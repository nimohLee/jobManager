package com.nimoh.jobManager.data.dto.board;

import com.nimoh.jobManager.data.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class BoardResponse {
    private final Long id;
    private final String title;
    private final User user;
    private final String content;
    private final String category;
}

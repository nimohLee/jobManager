package com.nimoh.jobManager.exception.crawler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CrawlerException extends RuntimeException {
    private final CrawlerErrorResult errorResult;
}

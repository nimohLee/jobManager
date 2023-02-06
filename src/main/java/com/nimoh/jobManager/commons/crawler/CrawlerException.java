package com.nimoh.jobManager.commons.crawler;

import com.nimoh.jobManager.commons.job.JobErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CrawlerException extends RuntimeException {
    private final CrawlerErrorResult errorResult;
}

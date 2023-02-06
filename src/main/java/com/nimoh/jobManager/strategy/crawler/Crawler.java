package com.nimoh.jobManager.strategy.crawler;

import com.nimoh.jobManager.commons.crawler.StrategyName;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Crawler {
    List<JobCrawlerDto> getList(Map<String, String> searchOption) throws IOException;

    StrategyName getStrategyName();
}


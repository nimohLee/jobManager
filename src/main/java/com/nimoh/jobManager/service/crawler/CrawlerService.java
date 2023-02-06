package com.nimoh.jobManager.service.crawler;

import com.nimoh.jobManager.commons.crawler.StrategyName;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface CrawlerService {
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption,  StrategyName strategyName) throws IOException;
}
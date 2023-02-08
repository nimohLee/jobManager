package com.nimoh.jobManager.service.crawler;

import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface JobSearchService {
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption) throws IOException;

}
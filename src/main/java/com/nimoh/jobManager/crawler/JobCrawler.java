package com.nimoh.jobManager.crawler;

import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;

public interface JobCrawler extends JobHtmlParser<JobCrawlerDto> {
    String getBaseUrl();
    String makeSearchListUrl(Map<String, String> searchOption);
    boolean checkSearchOption(Map<String, String> searchOption);

    boolean checkValidSortOption(String recruitSort);

    List<JobCrawlerDto> extracted(Elements itemRecruit, int resultCount);
}


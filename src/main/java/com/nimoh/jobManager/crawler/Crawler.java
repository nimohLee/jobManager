package com.nimoh.jobManager.crawler;

import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;

public interface Crawler {
    String getBaseUrl();
    String makeSearchListUrl(Map<String, String> searchOption);
    boolean checkSearchOption(Map<String, String> searchOption);

    boolean checkValidSortOption(String recruitSort);

    List<JobCrawlerDto> parseHTML(Document document);

    List<JobCrawlerDto> extracted(Elements itemRecruit, int resultCount);

}


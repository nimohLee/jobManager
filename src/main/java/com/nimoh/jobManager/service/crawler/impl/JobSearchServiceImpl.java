package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.exception.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.exception.crawler.CrawlerException;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import com.nimoh.jobManager.crawler.JobCrawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.JobSearchService;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JobSearchServiceImpl implements JobSearchService {
    private final JsoupConnection jsoupConnection;
    private final Map<String, JobCrawler> crawlerMap;

    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption, String crawlerName) {
        JobCrawler jobCrawler = crawlerMap.get(crawlerName);
        if (jobCrawler.checkSearchOption(searchOption)){
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }

        final String recruitSort = searchOption.get("recruitSort");

        if (jobCrawler.checkValidSortOption(recruitSort)){
            throw new CrawlerException(CrawlerErrorResult.OPTION_BAD_REQUEST);
        }

        String searchList = jobCrawler.makeSearchListUrl(searchOption);

        try {
            Document document = jsoupConnection.get(searchList);
            return jobCrawler.parseHTML(document);
        } catch (IOException e) {
            throw new CrawlerException(CrawlerErrorResult.IO_EXCEPTION);
        }
    }
}

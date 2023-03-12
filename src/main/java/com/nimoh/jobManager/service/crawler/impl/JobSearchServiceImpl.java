package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import com.nimoh.jobManager.crawler.JobCrawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.JobSearchService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class JobSearchServiceImpl implements JobSearchService {
    private final JsoupConnection jsoupConnection;
    private final Map<String, JobCrawler> crawlerMap;

    @Autowired
    public JobSearchServiceImpl(JsoupConnection jsoupConnection, Map<String, JobCrawler> crawlerMap) {
        this.jsoupConnection = jsoupConnection;
        this.crawlerMap = crawlerMap;
    }

    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption, String crawlerName) throws IOException {
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
            throw new IOException("JsoupConnect Error", e);
        }
    }
}

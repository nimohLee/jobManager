package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import com.nimoh.jobManager.crawler.Crawler;
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
    private final Map<String,Crawler> crawlerMap;

    @Autowired
    public JobSearchServiceImpl(JsoupConnection jsoupConnection, Map<String, Crawler> crawlerMap) {
        this.jsoupConnection = jsoupConnection;
        this.crawlerMap = crawlerMap;
    }

    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption, String crawlerName) throws IOException {
        Crawler crawler = crawlerMap.get(crawlerName);
        if (crawler.checkSearchOption(searchOption)){
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }

        final String recruitSort = searchOption.get("recruitSort");

        if (crawler.checkValidSortOption(recruitSort)){
            throw new CrawlerException(CrawlerErrorResult.OPTION_BAD_REQUEST);
        }

        String searchList = crawler.makeSearchListUrl(searchOption);

        try {
            Document document = jsoupConnection.get(searchList);
            return crawler.parseHTML(document);
        } catch (IOException e) {
            throw new IOException("JsoupConnect Error");
        }
    }
}

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

/**
 * 서비스 - 구직사이트 직무 검색
 *
 * @author nimoh
 */
@Service
@RequiredArgsConstructor
public class JobSearchServiceImpl implements JobSearchService {
    private final JsoupConnection jsoupConnection;
    private final Map<String, JobCrawler> crawlerMap;

    /**
     * 검색옵션을 받아 크롤링 후 결과 반환
     *
     * @param searchOption 검색 옵션이 들어있는 Map
     * @param crawlerName 구직사이트 별 크롤러 이름(Bean 이름) ex) saraminCrawler, jobKoreaCralwer...
     * @throws CrawlerException 검색 옵션이 null이거나, 정렬 옵션이 유요하지 않은 경우, jsoup 관련 IO예외 시 예외 발생
     * @return 검색결과가 List에 담겨 반환
     */
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

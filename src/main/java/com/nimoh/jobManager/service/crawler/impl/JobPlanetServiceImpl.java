package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.exception.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.exception.crawler.CrawlerException;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import com.nimoh.jobManager.crawler.JobPlanetHtmlParser;
import com.nimoh.jobManager.data.dto.crawler.JobPlanetDto;
import com.nimoh.jobManager.service.crawler.JobPlanetService;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 서비스 - 잡플래닛 크롤링
 *
 * @author nimoh
 */

@Service
public class JobPlanetServiceImpl implements JobPlanetService {
    Logger logger = LoggerFactory.getLogger(JobPlanetServiceImpl.class);

    private final JsoupConnection jsoupConnection;
    private final JobPlanetHtmlParser<JobPlanetDto> jobHtmlParser;

    private final String JOBPLANET_URL = "https://www.jobplanet.co.kr";

    @Autowired
    public JobPlanetServiceImpl(JsoupConnection jsoupConnection,JobPlanetHtmlParser<JobPlanetDto> jobHtmlParser) {
        this.jsoupConnection = jsoupConnection;
        this.jobHtmlParser = jobHtmlParser;
    }

    /**
     * 잡플래닛에서 회사 별점 가져옴
     *
     * @param companyName 회사명
     * @return 잡플래닛 조회 결과
     * @throws CrawlerException - companyName이 null인 경우 또는 Document관련 IOException 발생 시
     */
    @Override
    public JobPlanetDto getCompanyRate(String companyName){
        if (companyName == null) {
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }
        final String searchList = JOBPLANET_URL + "/search?category=search_new&search_keyword_hint_id=&_rs_con=seach&_rs_act=keyword_search"
                + "&query=" + companyName;
        logger.info(searchList);
        try {
            Document document = jsoupConnection.get(searchList);
            JobPlanetDto jobRate = jobHtmlParser.parseHTML(document); // 칼럼명
            logger.info("jobRate = " + jobRate.toString());
            return jobRate;
        } catch (IOException ioe) {
            throw new CrawlerException(CrawlerErrorResult.IO_EXCEPTION);
        }
    }
}

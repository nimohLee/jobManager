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

@Service
public class JobPlanetServiceImpl implements JobPlanetService {
    Logger logger = LoggerFactory.getLogger(JobPlanetServiceImpl.class);

    private final JsoupConnection jsoupConnection;
    private final JobPlanetHtmlParser<JobPlanetDto> jobHtmlParser;


    final private String JOBPLANET_URL = "https://www.jobplanet.co.kr";

    @Autowired
    public JobPlanetServiceImpl(JsoupConnection jsoupConnection,JobPlanetHtmlParser<JobPlanetDto> jobHtmlParser) {
        this.jsoupConnection = jsoupConnection;
        this.jobHtmlParser = jobHtmlParser;
    }

    @Override
    public JobPlanetDto getCompanyRate(String companyName) throws IOException {
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
        } catch (CrawlerException ce) {
            throw ce;
        } catch (IOException ioe) {
            throw ioe;
        }
    }
}

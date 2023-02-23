package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.data.dto.crawler.JobPlanetDto;
import com.nimoh.jobManager.service.crawler.JobPlanetService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JobPlanetServiceImpl implements JobPlanetService {
    Logger logger = LoggerFactory.getLogger(JobPlanetServiceImpl.class);
    final private String JOBPLANET_URL = "https://www.jobplanet.co.kr";

    @Override
    public JobPlanetDto getCompanyRate(String companyName) throws IOException {
        if (companyName == null) {
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }
        final String searchList = JOBPLANET_URL + "/search?category=search_new&search_keyword_hint_id=&_rs_con=seach&_rs_act=keyword_search"
                + "&query=" + companyName;
        logger.info(searchList);
        Connection conn = Jsoup.connect(searchList);
        try {
            Document document = conn.get();
            JobPlanetDto jobRate = parseHTML(document); // 칼럼명
            logger.info("jobRate = " + jobRate.toString());
            return jobRate;
        } catch (CrawlerException ce) {
            throw ce;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private JobPlanetDto parseHTML(Document document) {
        Element findCompany = document.select(".is_company_card .result_card").first();
        String companyName = findCompany.select(".tit").text();
        String companyUrl = findCompany.select(".tit").attr("href");
        String titleSub = findCompany.select(".tit_sub").text();
        String rate = findCompany.select(".rate_ty02").text();
        return JobPlanetDto.builder()
                .companyName(companyName)
                .companyUrl(JOBPLANET_URL + companyUrl)
                .titleSub(titleSub)
                .rate(rate)
                .build();
    }
}

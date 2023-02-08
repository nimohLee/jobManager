package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.JobSearchService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("IncruitCrawler")
public class IncruitCrawler implements JobSearchService {
    Logger logger = LoggerFactory.getLogger(IncruitCrawler.class);
    final private String INCRUIT_URL = "https://search.incruit.com";
    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption) throws IOException {
        if(searchOption.get("kw")==null||searchOption.get("sordCd")==null||searchOption.get("page")==null){
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }
        final int page = (Integer.parseInt(searchOption.get("page")) * 30 ) - 30;
        final String searchList = INCRUIT_URL+"/list/search.asp?col=job"
                + "&kw=" + searchOption.get("kw")
                + "&SortCd=" + searchOption.get("sordCd")
                + "&startno=" + page;
        logger.info(searchList);
        Connection conn = Jsoup.connect(searchList);
        try {
            Document document = conn.get();
            List<JobCrawlerDto> jobList = parseHTML(document); // 칼럼명
            logger.info("jobinfo = "+jobList.toString());
            return jobList;
        } catch (CrawlerException ce) {
            throw ce;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".c_col");
        logger.info("itemRecruit = " +String.valueOf(itemRecruit));
        return extracted(itemRecruit);
    }

    private List<JobCrawlerDto> extracted(Elements itemRecruit) {
        List<JobCrawlerDto> jobInfo = new ArrayList<>();
        for (Element element : itemRecruit) {
            String title = element.select(".cell_mid .cl_top a").text();
            String url = element.select(".cell_mid .cl_top a").attr("href");
            String jobDate = element.select(".cell_last .cl_btm span:first-child").text();
            String companyName = element.select(".cell_first .cl_top .cpname").text();
            String companyUrl = element.select(".cell_first .cl_top .cpname").attr("href");
            List<String> jobCondition = element.select(".cell_mid .cl_md span").eachText();
            JobCrawlerDto extractedJobs =
                    JobCrawlerDto.builder()
                            .title(title)
                            .url(url)
                            .companyName(companyName)
                            .companyUrl(companyUrl)
                            .jobDate(jobDate)
                            .jobCondition(jobCondition)
                            .build();
            jobInfo.add(extractedJobs);
        }
        return jobInfo;
    }
}

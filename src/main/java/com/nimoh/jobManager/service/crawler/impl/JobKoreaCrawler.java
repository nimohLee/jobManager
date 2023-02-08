package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.CrawlerService;
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

@Service("JobKoreaCrawler")
public class JobKoreaCrawler implements CrawlerService {
    Logger logger = LoggerFactory.getLogger(JobKoreaCrawler.class);
    final private String JOBKOREA_URL = "https://www.jobkorea.co.kr";
    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption) throws IOException {
        if(searchOption.get("stext")==null||searchOption.get("ord")==null||searchOption.get("pageNo")==null){
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }
        final String searchList = JOBKOREA_URL+"/Search/?tabType=recruit"
                + "&stext=" + searchOption.get("stext")
                + "&Page_No=" + searchOption.get("pageNo")
                + "&Ord=" + searchOption.get("ord");
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
        Elements itemRecruit = document.select(".list-post");
        logger.info("itemRecruit = " +String.valueOf(itemRecruit));
        String resultCount = document.select(".filter-text .dev_tot").text().replaceAll("[^0-9]","");
        // 뒤에 0이 붙어서 크롤링 되기 때문에 마지막 글자 제거
        int resultCountParse = Integer.parseInt(resultCount.substring(0,resultCount.length()-1));
        return extracted(itemRecruit, resultCountParse);
    }

    private List<JobCrawlerDto> extracted(Elements itemRecruit, int resultCount) {
        List<JobCrawlerDto> jobInfo = new ArrayList<>();
        for (Element element : itemRecruit) {
            String title = element.select(".post-list-info .title").attr("title");
            String url = element.select(".post-list-info .title").attr("href");
            String jobDate = element.select(".post-list-info .option .date").text();
            String companyName = element.select(".post-list-corp .name").attr("title");
            String companyUrl = element.select(".post-list-corp .name").attr("href");
            List<String> jobCondition = element.select(".post-list-info .option span").eachText();
            JobCrawlerDto extractedJobs =
                    JobCrawlerDto.builder()
                            .title(title)
                            .url(JOBKOREA_URL + url)
                            .companyName(companyName)
                            .companyUrl(JOBKOREA_URL + companyUrl)
                            .jobDate(jobDate)
                            .jobCondition(jobCondition)
                            .resultCount(resultCount)
                            .build();
            jobInfo.add(extractedJobs);
        }
        return jobInfo;
    }
}

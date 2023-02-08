package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.crawler.crawlerSort.JobKoreaRecruitSort;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("JobKoreaCrawler")
public class JobKoreaCrawler implements JobSearchService {
    Logger logger = LoggerFactory.getLogger(JobKoreaCrawler.class);
    final private String JOBKOREA_URL = "www.jobkorea.co.kr";

    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption) throws IOException {
        final String recruitSort = searchOption.get("recruitSort");

        if (searchOption.get("searchWord") == null || recruitSort == null || searchOption.get("recruitPage") == null) {
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }

        // 정렬옵션에 임의의 값이 들어왔을 때 예외처리
        JobKoreaRecruitSort[] jobKoreaRecruitSorts = JobKoreaRecruitSort.values();
        if (!Arrays.stream(jobKoreaRecruitSorts).anyMatch(sort -> sort.getResultSort().equals(recruitSort))) {
            throw new CrawlerException(CrawlerErrorResult.OPTION_BAD_REQUEST);
        }

        final String searchList = "https://" + JOBKOREA_URL + "/Search/?tabType=recruit"
                + "&stext=" + searchOption.get("searchWord")
                + "&Page_No=" + searchOption.get("recruitPage")
                + "&Ord=" + searchOption.get("recruitSort");
        logger.info(searchList);
        Connection conn = Jsoup.connect(searchList);
        try {
            Document document = conn.get();
            List<JobCrawlerDto> jobList = parseHTML(document); // 칼럼명
            logger.info("jobinfo = " + jobList.toString());
            return jobList;
        } catch (CrawlerException ce) {
            throw ce;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".recruit-info .list-post");
        logger.info("itemRecruit = " + String.valueOf(itemRecruit));
        String resultCount = document.select(".filter-text .dev_tot").text().replaceAll("[^0-9]", "");
        // 뒤에 0이 붙어서 크롤링 되기 때문에 마지막 글자 제거
        int resultCountParse = Integer.parseInt(resultCount.substring(0, resultCount.length() - 1));
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

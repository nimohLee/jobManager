package com.nimoh.jobManager.service.crawler;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlerServiceImpl implements CrawlerService{

    Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);
    final private String SARAMIN_URL = "www.saramin.co.kr";
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption) throws IOException {
        if(searchOption.get("searchWord")==null||searchOption.get("recruitPage")==null||searchOption.get("recruitSort")==null){
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }
        final String searchList = "https://www.saramin.co.kr/zf_user/search/recruit?search_done=y&search_optional_item=n&company_cd=0%2C1%2C2%2C3%2C4%2C5%2C6%2C7%2C9%2C10&show_applied=&quick_apply=&except_read=&ai_head_hunting=&mainSearch=n&loc_mcd=101000&inner_com_type=&recruitPageCount=20"
                + "&searchword=" +searchOption.get("searchWord")
                + "&recruitPage=" + searchOption.get("recruitPage")
                + "&recruitSort=" + searchOption.get("recruitSort");
        logger.info(searchList);
        Connection conn = Jsoup.connect(searchList);
        try {
            Document document = conn.get();
            List<JobCrawlerDto> jobList = getJobInfo(document); // 칼럼명
            logger.info("jobinfo = "+jobList.toString());
            return jobList;
        } catch (CrawlerException ce) {
            throw ce;
        } catch (IOException ioe) {
            throw ioe;
        }
    }
    private List<JobCrawlerDto> getJobInfo(Document document) {
        Elements itemRecruit = document.select(".item_recruit");
        logger.info(String.valueOf(itemRecruit));
        logger.info("itemRecruit size = "+String.valueOf(itemRecruit.size()));
        List<JobCrawlerDto> jobInfo = new ArrayList<>();
        for (Element element : itemRecruit) {
            String title = element.select(".area_job .job_tit a").attr("title");
            String url = element.select(".area_job .job_tit a").attr("href");
            String jobDate = element.select(".area_job .job_date span").text();
            String companyName = element.select(".area_corp .corp_name a").text();
            String companyUrl = element.select(".area_corp .corp_name a").attr("href");
            List<String> jobCondition = element.select(".area_job .job_condition span").eachText();

            JobCrawlerDto extractedJobs =

                    JobCrawlerDto.builder()
                            .title(title)
                            .url(SARAMIN_URL + url)
                            .companyName(companyName)
                            .companyUrl(SARAMIN_URL + companyUrl)
                            .jobDate(jobDate)
                            .jobCondition(jobCondition)
                            .build();

            jobInfo.add(extractedJobs);
        }
        return jobInfo;
    }

    private JobCrawlerDto extractInfo(String title, String url, String jobDate, List<String> jobCondition){
        return JobCrawlerDto.builder()
                .title(title)
                .url("www.saramin.co.kr"+url)
                .jobDate(jobDate)
                .jobCondition(jobCondition)
                .build();
    }
}

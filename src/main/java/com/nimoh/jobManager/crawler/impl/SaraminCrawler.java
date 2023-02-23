package com.nimoh.jobManager.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.crawler.crawlerSort.SaraminRecruitSort;
import com.nimoh.jobManager.crawler.Crawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SaraminCrawler implements Crawler {

    final private String baseUrl = "www.saramin.co.kr";

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public boolean checkSearchOption(Map<String, String> searchOption) {
        return searchOption.get("searchWord") == null || searchOption.get("recruitPage") == null || searchOption.get("recruitSort") == null;
    }

    /**
     * 정렬옵션이 SaraminRecruitSort Enum에 해당하지 않는다면 크롤러예외를 던집니다.
     * @param recruitSort
     */
    @Override
    public boolean checkValidSortOption(String recruitSort){
        SaraminRecruitSort[] saraminRecruitSorts = SaraminRecruitSort.values();
        return Arrays.stream(saraminRecruitSorts).noneMatch(sort -> sort.getResultSort().equals(recruitSort));
    }

    @Override
    public String makeSearchListUrl(Map<String, String> searchOption){
        if(searchOption.isEmpty()) return "empty";
        return "https://" + baseUrl +"/zf_user/search/recruit?search_done=y&search_optional_item=n&company_cd=0%2C1%2C2%2C3%2C4%2C5%2C6%2C7%2C9%2C10&show_applied=&quick_apply=&except_read=&ai_head_hunting=&mainSearch=n&loc_mcd=101000&inner_com_type=&recruitPageCount=20"
                + "&searchword=" +searchOption.get("searchWord")
                + "&recruitPage=" + searchOption.get("recruitPage")
                + "&recruitSort=" + searchOption.get("recruitSort");
    }

    public List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".item_recruit");
        try {
            int resultCount = Integer.parseInt(document.select(".cnt_result").text().replaceAll("[^0-9]",""));
            return extracted(itemRecruit, resultCount);
        }catch (NumberFormatException ne){
            throw new CrawlerException(CrawlerErrorResult.RESULT_NOT_FOUND);
        }
    }

    public List<JobCrawlerDto> extracted(Elements itemRecruit, int resultCount) {
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
                            .url(baseUrl + url)
                            .companyName(companyName)
                            .companyUrl(baseUrl + companyUrl)
                            .jobDate(jobDate)
                            .jobCondition(jobCondition)
                            .resultCount(resultCount)
                            .build();
            jobInfo.add(extractedJobs);
        }
        return jobInfo;
    }
}

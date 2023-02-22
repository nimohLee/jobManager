package com.nimoh.jobManager.service.crawler.impl;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.crawler.crawlerSort.SaraminRecruitSort;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.JobSearchService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

@Service("SaraminCrawler")
public class SaraminCrawler implements JobSearchService {
    final private String SARAMIN_URL = "www.saramin.co.kr";
    private final JsoupConnection jsoupConnection;

    @Autowired
    public SaraminCrawler(JsoupConnection jsoupConnection) {
        this.jsoupConnection = jsoupConnection;
    }

    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption) throws IOException {
        checkSearchOptionIsNull(searchOption);
        final String recruitSort = searchOption.get("recruitSort");
        checkValidSortOption(recruitSort);

        final String searchList = "https://" + SARAMIN_URL +"/zf_user/search/recruit?search_done=y&search_optional_item=n&company_cd=0%2C1%2C2%2C3%2C4%2C5%2C6%2C7%2C9%2C10&show_applied=&quick_apply=&except_read=&ai_head_hunting=&mainSearch=n&loc_mcd=101000&inner_com_type=&recruitPageCount=20"
                + "&searchword=" +searchOption.get("searchWord")
                + "&recruitPage=" + searchOption.get("recruitPage")
                + "&recruitSort=" + searchOption.get("recruitSort");

        try {
            Document document = jsoupConnection.get(searchList);
            List<JobCrawlerDto> jobList = parseHTML(document); // 칼럼명
            return jobList;
        } catch (IOException e) {
            throw new IOException("JsoupConnect Error");
        }
    }

    /**
     * 검색옵션에 null이 있는 지 체크. null이 있다면 크롤러예외를 던집니다.
     * @param searchOption
     */
    private void checkSearchOptionIsNull(Map<String, String> searchOption) {
        if(searchOption.get("searchWord")==null||searchOption.get("recruitPage")==null||searchOption.get("recruitSort")==null){
            throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }
    }

    /**
     * 정렬옵션이 SaraminRecruitSort Enum에 해당하지 않는다면 크롤러예외를 던집니다.
     * @param recruitSort
     */
    private void checkValidSortOption(String recruitSort){
        SaraminRecruitSort[] saraminRecruitSorts = SaraminRecruitSort.values();
        if(!Arrays.stream(saraminRecruitSorts).anyMatch(sort -> sort.getResultSort().equals(recruitSort))){
            throw new CrawlerException(CrawlerErrorResult.OPTION_BAD_REQUEST);
        }
    }

    private List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".item_recruit");
        try {
            int resultCount = Integer.parseInt(document.select(".cnt_result").text().replaceAll("[^0-9]",""));
            return extracted(itemRecruit, resultCount);
        }catch (NumberFormatException ne){
            throw new CrawlerException(CrawlerErrorResult.RESULT_NOT_FOUND);
        }
    }

    private List<JobCrawlerDto> extracted(Elements itemRecruit, int resultCount) {
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
                            .resultCount(resultCount)
                            .build();
            jobInfo.add(extractedJobs);
        }
        return jobInfo;
    }
}

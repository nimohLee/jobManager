package com.nimoh.jobManager.crawler.impl;

import com.nimoh.jobManager.exception.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.exception.crawler.CrawlerException;
import com.nimoh.jobManager.commons.crawler.crawlerSort.SaraminRecruitSort;
import com.nimoh.jobManager.crawler.JobHtmlParser;
import com.nimoh.jobManager.crawler.JobCrawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 구직사이트 사람인 크롤러
 *
 * @author nimoh
 */
@Component
public class SaraminJobCrawler implements JobCrawler, JobHtmlParser<JobCrawlerDto> {

    final private String baseUrl = "www.saramin.co.kr";

    /**
     * 크롤링할 사이트의 기본 URL을 반환
     *
     * @return 인크루트 기본 URL (baseUrl 필드)
     */
    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 검색옵션에 null이 있는 지 없는 지 확인
     *
     * @param searchOption null이 있는 지 확인하기 위한 검색옵션 map
     * @return 모두 null이 아닌 경우 true
     */
    @Override
    public boolean checkSearchOption(Map<String, String> searchOption) {
        return searchOption.get("searchWord") == null || searchOption.get("recruitPage") == null || searchOption.get("recruitSort") == null;
    }

    /**
     * 유효한 정렬 옵션인 지 확인
     *
     * @param recruitSort 정렬 옵션
     * @return 유효한 정렬 옵션이면 true
     */
    @Override
    public boolean checkValidSortOption(String recruitSort){
        SaraminRecruitSort[] saraminRecruitSorts = SaraminRecruitSort.values();
        return Arrays.stream(saraminRecruitSorts).noneMatch(sort -> sort.getResultSort().equals(recruitSort));
    }

    /**
     * 크롤링할 세부 URL 반환
     *
     * @param searchOption 검색 옵션
     * @return BaseUrl에 검색 옵션을 붙인 실제 크롤링 주소
     */
    @Override
    public String makeSearchListUrl(Map<String, String> searchOption){
        if(searchOption.isEmpty()) return "empty";
        return "https://" + baseUrl +"/zf_user/search/recruit?search_done=y&search_optional_item=n&company_cd=0%2C1%2C2%2C3%2C4%2C5%2C6%2C7%2C9%2C10&show_applied=&quick_apply=&except_read=&ai_head_hunting=&mainSearch=n&loc_mcd=101000&inner_com_type=&recruitPageCount=20"
                + "&searchword=" +searchOption.get("searchWord")
                + "&recruitPage=" + searchOption.get("recruitPage")
                + "&recruitSort=" + searchOption.get("recruitSort");
    }

    /**
     * HTML 파서
     *
     * @param document 파싱할 문서
     * @return 파싱된 회사 결과 리스트
     */
    @Override
    public List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".item_recruit");
        try {
            int resultCount = Integer.parseInt(document.select(".cnt_result").text().replaceAll("[^0-9]",""));
            return extracted(itemRecruit, resultCount);
        }catch (NumberFormatException ne){
            throw new CrawlerException(CrawlerErrorResult.RESULT_NOT_FOUND);
        }
    }

    /**
     * 문서에서 태그 내용 추출
     *
     * @param itemRecruit 선택된 태크 리스트
     * @param count
     * @return 추출된 구직 공고 결과
     */
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

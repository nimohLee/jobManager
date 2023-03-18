package com.nimoh.jobManager.crawler.impl;

import com.nimoh.jobManager.commons.crawler.crawlerSort.IncruitRecruitSort;
import com.nimoh.jobManager.crawler.JobHtmlParser;
import com.nimoh.jobManager.crawler.JobCrawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.exception.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.exception.crawler.CrawlerException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 구직사이트 인크루트 크롤러
 *
 * @author nimoh
 */
@Component
public class IncruitCralwer implements JobCrawler, JobHtmlParser<JobCrawlerDto> {

    final private String baseUrl = "search.incruit.com";

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
        return searchOption.get("searchWord") != null && searchOption.get("recruitPage") != null && searchOption.get("recruitSort") != null;
    }

    /**
     * 유효한 정렬 옵션인 지 확인
     *
     * @param recruitSort 정렬 옵션
     * @return 유효한 정렬 옵션이면 true
     */
    @Override
    public boolean checkValidSortOption(String recruitSort) {
        IncruitRecruitSort[] incruitRecruitSort = IncruitRecruitSort.values();
        return Arrays.stream(incruitRecruitSort).noneMatch(sort -> sort.getResultSort().equals(recruitSort));
    }

    /**
     * HTML 파서
     *
     * @param document 파싱할 문서
     * @return 파싱된 회사 결과 리스트
     */
    @Override
    public List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".c_col");
        return extracted(itemRecruit, 0);
    }

    /**
     * 크롤링할 세부 URL 반환
     *
     * @param searchOption 검색 옵션
     * @return BaseUrl에 검색 옵션을 붙인 실제 크롤링 주소
     */
    @Override
    public String makeSearchListUrl(Map<String, String> searchOption){
        if(searchOption.isEmpty()) throw new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        final int page = (Integer.parseInt(searchOption.get("recruitPage")) * 30) - 30;
        return "https://" + baseUrl + "/list/search.asp?col=job"
                + "&kw=" + searchOption.get("searchWord")
                + "&SortCd=" + searchOption.get("recruitSort")
                + "&startno=" + page;
    }

    /**
     * 문서에서 태그 내용 추출
     *
     * @param itemRecruit 선택된 태크 리스트
     * @param count
     * @return 추출된 구직 공고 결과
     */
    @Override
    public List<JobCrawlerDto> extracted(Elements itemRecruit, int count) {
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
                            .companyName(companyName.replaceAll("https://", ""))
                            .companyUrl(companyUrl.replaceAll("https://", ""))
                            .jobDate(jobDate)
                            .jobCondition(jobCondition)
                            .build();
            jobInfo.add(extractedJobs);
        }
        return jobInfo;
    }
}

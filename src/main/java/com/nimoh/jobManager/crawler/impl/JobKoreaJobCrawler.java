package com.nimoh.jobManager.crawler.impl;
import com.nimoh.jobManager.commons.crawler.crawlerSort.JobKoreaRecruitSort;
import com.nimoh.jobManager.crawler.JobHtmlParser;
import com.nimoh.jobManager.crawler.JobCrawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 구직사이트 잡코리아 크롤러
 *
 * @author nimoh
 */
@Component
public class JobKoreaJobCrawler implements JobCrawler, JobHtmlParser<JobCrawlerDto> {
    final private String baseUrl = "www.jobkorea.co.kr";

    /**
     * 크롤링할 사이트의 기본 URL을 반환
     *
     * @return 잡코리아 기본 URL (baseUrl 필드)
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
        JobKoreaRecruitSort[] jobKoreaRecruitSorts = JobKoreaRecruitSort.values();
        return Arrays.stream(jobKoreaRecruitSorts).noneMatch(sort -> sort.getResultSort().equals(recruitSort));
    }

    /**
     * 크롤링할 세부 URL 반환
     *
     * @param searchOption 검색 옵션
     * @return BaseUrl에 검색 옵션을 붙인 실제 크롤링 주소
     */
    @Override
    public String makeSearchListUrl(Map<String, String> searchOption) {
        if(searchOption.isEmpty()) return "empty";
        return "https://" + baseUrl + "/Search/?tabType=recruit"
                + "&stext=" + searchOption.get("searchWord")
                + "&Page_No=" + searchOption.get("recruitPage")
                + "&Ord=" + searchOption.get("recruitSort");
    }

    /**
     * HTML 파서
     *
     * @param document 파싱할 문서
     * @return 파싱된 회사 결과 리스트
     */
    public List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".recruit-info .list-post");
        String resultCount = document.select(".filter-text .dev_tot").text().replaceAll("[^0-9]", "");
        // 뒤에 0이 붙어서 크롤링 되기 때문에 마지막 글자 제거
        int resultCountParse = Integer.parseInt(resultCount.substring(0, resultCount.length() - 1));
        return extracted(itemRecruit, resultCountParse);
    }

    /**
     * 문서에서 태그 내용 추출
     *
     * @param itemRecruit 선택된 태크 리스트
     * @param resultCount 검색 결과 개수
     * @return 추출된 구직 공고 결과
     */
    public List<JobCrawlerDto> extracted(Elements itemRecruit, int resultCount) {
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
                            .url(this.baseUrl + url)
                            .companyName(companyName)
                            .companyUrl(this.baseUrl + companyUrl)
                            .jobDate(jobDate)
                            .jobCondition(jobCondition)
                            .resultCount(resultCount)
                            .build();
            jobInfo.add(extractedJobs);
        }
        return jobInfo;
    }
}

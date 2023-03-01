package com.nimoh.jobManager.crawler.impl;
import com.nimoh.jobManager.commons.crawler.crawlerSort.JobKoreaRecruitSort;
import com.nimoh.jobManager.crawler.Crawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class JobKoreaCrawler implements Crawler {
    final private String baseUrl = "www.jobkorea.co.kr";

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public boolean checkSearchOption(Map<String, String> searchOption) {
        return searchOption.get("searchWord") == null || searchOption.get("recruitPage") == null || searchOption.get("recruitSort") == null;
    }
    /**
     * 정렬옵션이 JobKoreaRecruitSort Enum에 해당하지 않는다면 크롤러예외를 던집니다.
     * @param recruitSort
     */
    @Override
    public boolean checkValidSortOption(String recruitSort){
        JobKoreaRecruitSort[] jobKoreaRecruitSorts = JobKoreaRecruitSort.values();
        return Arrays.stream(jobKoreaRecruitSorts).noneMatch(sort -> sort.getResultSort().equals(recruitSort));
    }

    @Override
    public String makeSearchListUrl(Map<String, String> searchOption) {
        if(searchOption.isEmpty()) return "empty";
        return "https://" + baseUrl + "/Search/?tabType=recruit"
                + "&stext=" + searchOption.get("searchWord")
                + "&Page_No=" + searchOption.get("recruitPage")
                + "&Ord=" + searchOption.get("recruitSort");
    }

    public List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".recruit-info .list-post");
        String resultCount = document.select(".filter-text .dev_tot").text().replaceAll("[^0-9]", "");
        // 뒤에 0이 붙어서 크롤링 되기 때문에 마지막 글자 제거
        int resultCountParse = Integer.parseInt(resultCount.substring(0, resultCount.length() - 1));
        return extracted(itemRecruit, resultCountParse);
    }

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

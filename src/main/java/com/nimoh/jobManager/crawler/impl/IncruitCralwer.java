package com.nimoh.jobManager.crawler.impl;

import com.nimoh.jobManager.commons.crawler.crawlerSort.IncruitRecruitSort;
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
public class IncruitCralwer implements Crawler {

    final private String baseUrl = "search.incruit.com";

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public boolean checkSearchOption(Map<String, String> searchOption) {
        return searchOption.get("searchWord") != null && searchOption.get("recruitPage") != null && searchOption.get("recruitSort") != null;
    }

    @Override
    public boolean checkValidSortOption(String recruitSort) {
        IncruitRecruitSort[] incruitRecruitSort = IncruitRecruitSort.values();
        return Arrays.stream(incruitRecruitSort).noneMatch(sort -> sort.getResultSort().equals(recruitSort));
    }

    @Override
    public List<JobCrawlerDto> parseHTML(Document document) {
        Elements itemRecruit = document.select(".c_col");
        return extracted(itemRecruit, 0);
    }

    @Override
    public String makeSearchListUrl(Map<String, String> searchOption){
        if(searchOption.isEmpty()) return "empty";
        final int page = (Integer.parseInt(searchOption.get("recruitPage")) * 30) - 30;
        return "https://" + baseUrl + "/list/search.asp?col=job"
                + "&kw=" + searchOption.get("searchWord")
                + "&SortCd=" + searchOption.get("recruitSort")
                + "&startno=" + page;
    }

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

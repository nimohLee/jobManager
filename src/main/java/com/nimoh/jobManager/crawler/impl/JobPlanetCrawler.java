package com.nimoh.jobManager.crawler.impl;

import com.nimoh.jobManager.crawler.JobPlanetHtmlParser;
import com.nimoh.jobManager.data.dto.crawler.JobPlanetDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

/**
 * 잡플래닛 크롤러
 *
 * @author nimoh
 */
@Component
public class JobPlanetCrawler implements JobPlanetHtmlParser<JobPlanetDto> {
    final private String JOBPLANET_URL = "https://www.jobplanet.co.kr";

    /**
     * 문서에서 필요한 회사 정보 추출
     *
     * @param document 크롤링한 문서
     * @return 검색한 회사
     */
    @Override
    public JobPlanetDto parseHTML(Document document) {
        Element findCompany = document.select(".is_company_card .result_card").first();
        String companyName = findCompany.select(".tit").text();
        String companyUrl = findCompany.select(".tit").attr("href");
        String titleSub = findCompany.select(".tit_sub").text();
        String rate = findCompany.select(".rate_ty02").text();

        return JobPlanetDto.builder()
                .companyName(companyName)
                .companyUrl(JOBPLANET_URL + companyUrl)
                .titleSub(titleSub)
                .rate(rate)
                .build();
    }
}

package com.nimoh.jobManager.service;

import com.nimoh.jobManager.exception.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.exception.crawler.CrawlerException;
import com.nimoh.jobManager.commons.crawler.crawlerSort.SaraminRecruitSort;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;

import com.nimoh.jobManager.crawler.JobPlanetHtmlParser;
import com.nimoh.jobManager.crawler.impl.SaraminJobCrawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.data.dto.crawler.JobPlanetDto;
import com.nimoh.jobManager.service.crawler.impl.JobPlanetServiceImpl;
import com.nimoh.jobManager.service.crawler.impl.JobSearchServiceImpl;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrawlerServiceTest {

    @Mock
    private JsoupConnection jsoupConnection;

    @Mock
    private Map<String, com.nimoh.jobManager.crawler.JobCrawler> crawlerMap;
    @Mock
    private com.nimoh.jobManager.crawler.JobCrawler jobCrawler;

    @InjectMocks
    private JobSearchServiceImpl jobSearchService;

    @InjectMocks
    private JobPlanetServiceImpl jobPlanetService;

    @BeforeEach
    void init(){
        jobCrawler = mock(SaraminJobCrawler.class);
    }

    @Nested
    @DisplayName("구직사이트 테스트")
    class JobJobCrawler {
        @Test
        void 검색결과조회실패_검색옵션이NULL임() {
            //given
            doReturn(new SaraminJobCrawler()).when(crawlerMap).get(any());
            Map<String, String> searchOption = new HashMap<>();
            searchOption.put("null", null);
            //when
            CrawlerException crawlerException = Assertions.assertThrows(CrawlerException.class, () -> jobSearchService.getSearchList(searchOption, "saraminCrawler"));
            //then
            assertThat(crawlerException.getErrorResult()).isEqualTo(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }

        @Test
        void 검색결과조회실패_정렬옵션이잘못됨() {
            //given
            doReturn(new SaraminJobCrawler()).when(crawlerMap).get(any());
            Map<String, String> searchOption = new HashMap<>();
            searchOption.put("searchWord", "검색어");
            searchOption.put("recruitPage", "페이지");
            searchOption.put("recruitSort", "잘못된정렬옵션");
            //when
            CrawlerException crawlerException = Assertions.assertThrows(CrawlerException.class, () -> jobSearchService.getSearchList(searchOption, "saraminCrawler"));
            //then
            assertThat(crawlerException.getErrorResult()).isEqualTo(CrawlerErrorResult.OPTION_BAD_REQUEST);
        }

        @Test
        void 검색결과조회실패_jsoup파싱중IOException발생() throws IOException {
            //given
            doReturn(jobCrawler).when(crawlerMap).get(any());

            Map<String, String> searchOption = new HashMap<>();

            searchOption.put("searchWord", "검색어");
            searchOption.put("recruitPage", "페이지");
            searchOption.put("recruitSort", SaraminRecruitSort.ACCURACY.getResultSort());

            doThrow(IOException.class).when(jsoupConnection).get(any());
            //when
            IOException ioException = Assertions.assertThrows(IOException.class, () -> jobSearchService.getSearchList(searchOption, "saraminCrawler"));
            //then
            assertThat(ioException.getMessage()).isEqualTo("JsoupConnect Error");
        }

        @Test
        void 검색결과조회성공() throws IOException {
            //given
            SaraminJobCrawler crawler = mock(SaraminJobCrawler.class);
            Map<String, String> searchOption = new HashMap<>();
            Document document = new Document("www.saramin.com");

            doReturn(crawler).when(crawlerMap).get(any());
            doReturn(document).when(jsoupConnection).get(any());
            doReturn(Arrays.asList(JobCrawlerDto.builder().build())).when(crawler).parseHTML(any());

            searchOption.put("searchWord", "검색어");
            searchOption.put("recruitPage", "페이지");
            searchOption.put("recruitSort", SaraminRecruitSort.ACCURACY.getResultSort());

            //when

            List<JobCrawlerDto> result = jobSearchService.getSearchList(searchOption, "saraminCrawler");
            //then
            assertThat(result.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("잡플래닛 테스트")
    class JobPlanetTest {

        @Mock
        private JsoupConnection jsoupConnection;
        @Mock
        private JobPlanetHtmlParser<JobPlanetDto> jobHtmlParser;
        @InjectMocks
        private JobPlanetServiceImpl jobPlanetService;

        private final String URL = "https://www.jobplanet.co.kr";


        @Test
        void 회사조회실패_companyName이null() throws IOException {
            //given
            String companyName = null;
            //when
            CrawlerException result = Assertions.assertThrows(CrawlerException.class, () -> jobPlanetService.getCompanyRate(companyName));
            //then
            assertThat(result.getErrorResult()).isEqualTo(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
        }

        @Test
        void 회사조회성공() throws IOException {
            //given
            String companyName = "카카오";
            Document document = new Document("www.jobPlanet.com");
            doReturn(document).when(jsoupConnection).get(any());
            doReturn(JobPlanetDto.builder().build()).when(jobHtmlParser).parseHTML(any());
            //when
            JobPlanetDto result = jobPlanetService.getCompanyRate(companyName);
            //then
            assertThat(result).isInstanceOf(JobPlanetDto.class);
        }
    }
}

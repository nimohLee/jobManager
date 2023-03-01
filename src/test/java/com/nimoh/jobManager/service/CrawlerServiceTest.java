package com.nimoh.jobManager.service;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.crawler.crawlerSort.SaraminRecruitSort;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;

import com.nimoh.jobManager.crawler.Crawler;
import com.nimoh.jobManager.crawler.impl.SaraminCrawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.impl.JobSearchServiceImpl;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrawlerServiceTest {

    @Mock
    private JsoupConnection jsoupConnection;
    @Mock
    private Map<String, Crawler> crawlerMap;
    @Mock
    private Crawler crawler;

    @InjectMocks
    private JobSearchServiceImpl jobSearchService;

    @BeforeEach
    void init(){
        crawler = mock(SaraminCrawler.class);
    }

    @Test
    void 검색결과조회실패_검색옵션이NULL임() {
        //given
        doReturn(new SaraminCrawler()).when(crawlerMap).get(any());
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
        doReturn(new SaraminCrawler()).when(crawlerMap).get(any());
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
        doReturn(crawler).when(crawlerMap).get(any());

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
        SaraminCrawler crawler = mock(SaraminCrawler.class);
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

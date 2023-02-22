package com.nimoh.jobManager.service;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.impl.SaraminCrawler;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrawlerServiceTest {

    @Mock
    private JsoupConnection jsoupConnection;

    @InjectMocks
    private SaraminCrawler saraminCrawler;

    @Test
    void 검색목록가져오기실패_옵션값에NULL이있음() throws IOException {
        //given
        Map<String, String> searchOption = new HashMap<>();
        searchOption.put("searchWord","백엔드");
        //when
        CrawlerException resultException = Assertions.assertThrows(CrawlerException.class, () -> saraminCrawler.getSearchList(searchOption));
        //then
        assertThat(resultException.getErrorResult()).isEqualTo(CrawlerErrorResult.OPTION_NULL_EXCEPTION);
    }

    @Test
    void 검색목록가져오기실패_유효하지않은정렬기준() {
        //given
        Map<String, String> searchOption = new HashMap<>();
        searchOption.put("searchWord","백엔드");
        searchOption.put("recruitPage","1");
        searchOption.put("recruitSort","잘못된정렬기준");
        //when
        CrawlerException resultException = Assertions.assertThrows(CrawlerException.class, () -> saraminCrawler.getSearchList(searchOption));
        //then
        assertThat(resultException.getErrorResult()).isEqualTo(CrawlerErrorResult.OPTION_BAD_REQUEST);
    }

    @Test
    void 검색목록가져오기실패_IOEXCEPTION발생() throws IOException {
        //given
        Map<String, String> searchOption = new HashMap<>();
        searchOption.put("searchWord","백엔드");
        searchOption.put("recruitPage","1");
        searchOption.put("recruitSort","relation");
        doThrow(IOException.class).when(jsoupConnection).get(any());
        //when
        IOException result = Assertions.assertThrows(IOException.class, () -> saraminCrawler.getSearchList(searchOption));
        //then
        assertThat(result.getMessage()).isEqualTo("JsoupConnect Error");
    }

    @Test
    void 검색목록가져오기실패_결과없음() throws IOException {
        //given
        Map<String, String> searchOption = new HashMap<>();
        searchOption.put("searchWord","백엔드");
        searchOption.put("recruitPage","1");
        searchOption.put("recruitSort","relation");
        Document document= new Document("fakeUrl");
        doReturn(document).when(jsoupConnection).get(anyString());
        //when
        CrawlerException result = Assertions.assertThrows(CrawlerException.class, () -> saraminCrawler.getSearchList(searchOption));
        //then
        assertThat(result.getErrorResult()).isEqualTo(CrawlerErrorResult.RESULT_NOT_FOUND);
    }

}

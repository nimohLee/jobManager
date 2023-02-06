package com.nimoh.jobManager.controller;

import com.google.gson.Gson;
import com.nimoh.jobManager.commons.GlobalExceptionHandler;
import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.crawler.RecruitSort;
import com.nimoh.jobManager.commons.job.JobErrorResult;
import com.nimoh.jobManager.commons.job.JobException;
import com.nimoh.jobManager.crawler.Crawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.CralwerServiceImpl;
import com.nimoh.jobManager.service.crawler.CrawlerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CrawlerControllerTest {

    @Autowired
    MockMvc mockMvc;

    Gson gson;

    @Mock
    Crawler crawler;

    @Mock
    CralwerServiceImpl crawlerService;

    @InjectMocks
    CrawlerController controller;

    Logger logger = LoggerFactory.getLogger(CrawlerControllerTest.class);

    @BeforeEach
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void 사람인지원조회성공() throws Exception {
        //given
        final String url = "/api/v1/crawler/saramin";
        doReturn(Arrays.asList(JobCrawlerDto.builder().build(),JobCrawlerDto.builder().build())).when(crawlerService).getSearchList(any());
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .params(getSaraminParam())
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 사람인지원조회실패_sevice에서throw() throws Exception {
        //given
        final String url = "/api/v1/crawler/saramin";
        doThrow(new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION))
                .when(crawlerService).getSearchList(any());
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .params(getSaraminParam())
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    private MultiValueMap<String, String> getSaraminParam() {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("searchWord","백엔드");
        requestParam.add("recruitPage","1");
        requestParam.add("recruitPageCount","40");
        requestParam.add("recruitSort",RecruitSort.ACCURACY.getResultSort());
        return requestParam;
    }
}

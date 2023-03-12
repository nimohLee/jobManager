package com.nimoh.jobManager.controller;

import com.google.gson.Gson;
import com.nimoh.jobManager.commons.GlobalExceptionHandler;
import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.service.crawler.JobPlanetService;
import com.nimoh.jobManager.service.crawler.JobSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class JobCrawlerControllerTest {

    private MockMvc mockMvc;
    private Gson gson;

    @InjectMocks
    private CrawlerController crawlerController;

    @Mock
    private JobSearchService jobSearchService;

    @Mock
    private JobPlanetService jobPlanetService;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(crawlerController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void 사람인크롤링실패_service에서throw() throws Exception {
        //given
        String url = "/api/v1/crawler/saramin";
        MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<>();
        mapParams.add("query","query");
        doThrow(new CrawlerException(CrawlerErrorResult.OPTION_BAD_REQUEST)).when(jobSearchService).getSearchList(mapParams.toSingleValueMap(), "saraminCrawler");
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .params(mapParams)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 사람인크롤링성공() throws Exception {
        //given
        String url = "/api/v1/crawler/saramin";
        MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<>();
        mapParams.add("query","query");
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .params(mapParams)
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 잡플래닛크롤링실패_service에서throw() throws Exception {
        //given
        String url = "/api/v1/crawler/jobplanet";
        doThrow(new CrawlerException(CrawlerErrorResult.OPTION_NULL_EXCEPTION)).when(jobPlanetService).getCompanyRate(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("companyName","회사이름")
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }
    @Test
    void 잡플래닛크롤링성공() throws Exception {
        //given
        String url = "/api/v1/crawler/jobplanet";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("companyName","회사이름")
        );
        //then
        resultActions.andExpect(status().isOk());
    }
}

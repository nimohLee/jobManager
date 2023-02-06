package com.nimoh.jobManager.controller;

import com.google.gson.Gson;
import com.nimoh.jobManager.commons.GlobalExceptionHandler;
import com.nimoh.jobManager.commons.crawler.RecruitSort;
import com.nimoh.jobManager.crawler.Crawler;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CrawlerControllerTest {

    @Autowired
    MockMvc mockMvc;

    Gson gson;

    @Mock
    Crawler crawler;

    @InjectMocks
    CrawlerController controller;

    Logger logger = LoggerFactory.getLogger(CrawlerControllerTest.class);

    @BeforeEach
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void 사람인지원조회실패_요청값부족() throws Exception {
        //given
        final String url = "/api/v1/crawler/saramin";
        final JobCrawlerDto jobSearchDto = JobCrawlerDto.builder().recruitPageCount("40").recruitSort(RecruitSort.RELATION.getResultSort()).build();
        logger.info(String.valueOf(jobSearchDto));
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(jobSearchDto))
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }
}

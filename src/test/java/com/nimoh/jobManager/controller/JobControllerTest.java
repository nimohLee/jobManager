package com.nimoh.jobManager.controller;

import com.google.gson.Gson;
import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;
import com.nimoh.jobManager.exception.job.JobErrorResult;
import com.nimoh.jobManager.exception.job.JobException;
import com.nimoh.jobManager.exception.GlobalExceptionHandler;
import com.nimoh.jobManager.data.dto.user.UserResponse;
import com.nimoh.jobManager.data.entity.Skill;
import com.nimoh.jobManager.service.api.RestTemplateService;
import com.nimoh.jobManager.service.job.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class JobControllerTest {

    private MockMvc mockMvc;
    private Gson gson;

    @InjectMocks
    private JobController jobController;
    @Mock
    private JobServiceImpl jobService;
    @Mock
    private RestTemplateService restTemplateService;
    @Mock
    private Authentication authentication;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @WithMockUser
    public void 유저별지원조회성공() throws Exception {
        //given
        final String url = "/api/v1/job";
        doReturn(Arrays.asList(
                JobResponse.builder().build(),
                JobResponse.builder().build(),
                JobResponse.builder().build()
        )).when(jobService).findByUser(any());
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void 조건별지원조회실패_service에서throw() throws Exception {
        //given
        final String url = "/api/v1/job/cond";
        doThrow(new JobException(JobErrorResult.UNKNOWN_EXCEPTION)).when(jobService).findByCond(any(),any());
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    public void 조건별지원조회성공() throws Exception {
        //given
        final String url = "/api/v1/job/cond";
        doReturn(Arrays.asList(
                JobResponse.builder().build(),
                JobResponse.builder().build(),
                JobResponse.builder().build()
        )).when(jobService).findByCond(any(),any());
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name","신발")
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 지원등록성공() throws Exception {
        //given
        final String url = "/api/v1/job";
        JobRequest jobRequest = jobRequest();
        Map<String, String> geoCode = new HashMap<>();
        geoCode.put("x", "127.1233");
        geoCode.put("y", "35.1233");
        given(jobService.save(any(), any())).willReturn(jobResponse());
        given(restTemplateService.getGeocode(any())).willReturn(geoCode);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .sessionAttr("sid", UserResponse.builder().build())
                        .content(jobRequestJson())
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void 지원삭제실패_삭제권한없음() throws Exception {
        //given
        final String url = "/api/v1/job/1";
        lenient().doThrow(new JobException(JobErrorResult.NO_PERMISSION))
                .when(jobService)
                .delete(any(), any());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
        );
        //then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    public void 지원삭제성공() throws Exception {
        //given
        String url = "/api/v1/job/1";
        doReturn(true).when(jobService).delete(any(), any());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
        );
        //then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void 지원수정실패_유저헤더없음() throws Exception {
        //given
        final String url = "/api/v1/job/1";

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 지원수정실패_수정권한없음() throws Exception {
        //given
        final String url = "/api/v1/job/1";
        doThrow(new JobException(JobErrorResult.NO_PERMISSION))
                .when(jobService)
                .update(any(JobRequest.class), any(), any());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .sessionAttr("sid", UserResponse.builder().id(1L).build())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(JobRequest.builder().build()))
        );
        //then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    public void 지원수정성공() throws Exception {
        //given
        final String url = "/api/v1/job/1";
        final Long userId = 1L;
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .sessionAttr("sid", UserResponse.builder().id(userId).build())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobRequestJson())
        );
        //then
        resultActions.andExpect(status().isCreated());
    }

    private JobResponse jobResponse() throws ParseException {

        return JobResponse.builder()
                .id(1L)
                .companyName("test")
                .huntingSite("test site")
                .employeesNumber(100)
                .location("서울특별시")
                .position("백엔드")
                .requiredCareer("신입")
                .primarySkill(Arrays.asList(Skill.builder().build()))
                .applyDate(LocalDate.now())
                .result("합격")
                .note("좋은 회사")
                .build();
    }

    private JobRequest jobRequest() {
        return JobRequest.builder()
                .companyName("test")
                .huntingSite("test site")
                .employeesNumber(100)
                .location("서울특별시")
                .position("백엔드")
                .requiredCareer("신입")
                .primarySkill(Arrays.asList(Skill.builder().build()))
                .applyDate(LocalDate.now())
                .result("합격")
                .note("좋은 회사")
                .build();
    }

    private String jobRequestJson() {
        return "{ " +
                "\"companyName\" : \"test\"," +
                "\"huntingSite\" : \"test site\"," +
                "\"employeesNumber\" : 100," +
                "\"location\" : \"서울특별시\"," +
                "\"position\" : \"백엔드\"," +
                "\"requiredCareer\" : \"신입\"," +
                "\"primarySkill\" : [ " +
                "{" +
                "\"name\" : \"Java\"" +
                "}" +
                "], " +
                "\"applyDate\" : \"2022-11-30T12:10:11\"," +
                "\"result\" : \"합격\"," +
                "\"note\" : \"좋은 회사\"" +
                "}";
    }
}

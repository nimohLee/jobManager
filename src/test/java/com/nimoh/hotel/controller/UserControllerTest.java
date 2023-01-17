package com.nimoh.hotel.controller;

import com.google.gson.Gson;
import com.nimoh.hotel.constants.Headers;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.data.dto.user.UserSignUpRequest;
import com.nimoh.hotel.commons.GlobalExceptionHandler;
import com.nimoh.hotel.commons.user.UserErrorResult;
import com.nimoh.hotel.commons.user.UserException;
import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    private Gson gson;
    protected MockHttpServletRequest request;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
        request = new MockHttpServletRequest();
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("sid",sessionId);
        request.setCookies(cookie);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void 회원가입실패_요청값없음() throws Exception {
        //given
        String url = "/api/v1/user";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입실패_아이디유효성() throws Exception {
        //given
        String url = "/api/v1/user";
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("fail")
                .name("test")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }
    @Test
    public void 회원가입실패_이름유효성() throws Exception {
        //given
        String url = "/api/v1/user";
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("nimoh123")
                .name("")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }
    @Test
    public void 회원가입실패_비밀번호유효성() throws Exception {
        //given
        String url = "/api/v1/user";
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("nimoh123")
                .name("test")
                .password("wrong")
                .email("nimoh@email.com")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입실패_이메일유효성() throws Exception {
        //given
        String url = "/api/v1/user";
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("fail")
                .name("test")
                .password("12345678910a")
                .email("nimoh")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입실패_service에서throw() throws Exception {
        //given
        String url = "/api/v1/user";
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("nimoh123")
                .name("test")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
        doThrow(new UserException(UserErrorResult.DUPLICATED_USER_ID)).when(userService).signUp(request);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );
        //then
        resultActions.andExpect(status().isConflict());
    }

    @Test
    public void 회원가입성공() throws Exception {
        //given
        String url = "/api/v1/user";
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("nimoh123")
                .name("test")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );
        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void 회원탈퇴실패_세션없음() throws Exception {
        //given
        String url = "/api/v1/user";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
        );
        //then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    public void 회원탈퇴실패_service에서throw() throws Exception {
        //given
        String url = "/api/v1/user";
        doThrow(new RuntimeException()).when(userService).deleteById(1L);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .sessionAttr("sid",User.builder().id(1L).build())
        );
        //then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    public void 회원탈퇴성공() throws Exception {
        //given
        String url = "/api/v1/user";
        doReturn(true).when(userService).deleteById(1L);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .sessionAttr("sid", User.builder().id(1L).build())
        );
        //then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void 로그인실패_요청값없음() throws Exception {
        //given
        String url = "/api/v1/user/login";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 로그인실패_세션이미있음() {
//        //given
//        String url = "/api/v1/user/login";
//        doReturn(UserResponse.builder().build()).when(userService).login(any());
//        //when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"uid\": \"nimoh123\",\"password\": \"password123\"}")
//
//        );
//        //then
//        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 로그인성공() throws Exception{
        //given
        String url = "/api/v1/user/login";
        doReturn(UserResponse.builder().build()).when(userService).login(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"uid\": \"nimoh123\",\"password\": \"password123\"}")

        );
        //then
        resultActions.andExpect(status().isOk());
    }


    private UserSignUpRequest request(){
        return UserSignUpRequest.builder()
                .uid("nimoh123")
                .name("test")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
    }
}

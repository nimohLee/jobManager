package com.nimoh.jobManager.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nimoh.jobManager.commons.GlobalExceptionHandler;
import com.nimoh.jobManager.commons.cookie.CookieProvider;
import com.nimoh.jobManager.commons.token.TokenErrorResult;
import com.nimoh.jobManager.commons.token.TokenException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.service.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TokenControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private TokenService tokenService;

    @Mock
    private CookieProvider cookieProvider;

    protected MockHttpServletRequest request;

    private String WRONG_REFRESH_TOKEN = "1123123";


    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).setControllerAdvice(new GlobalExceptionHandler()).build();
        request = new MockHttpServletRequest();
        request.setCookies(new Cookie("refreshToken", WRONG_REFRESH_TOKEN));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void 토큰재발급실패_유효하지않은refresh토큰() throws Exception {
        //given
        final String url = "/api/v1/token";
        final String cookieValue = "some value";
        final Cookie refreshToken = new Cookie("refreshToken", cookieValue);
        doThrow(new TokenException(TokenErrorResult.INVALID_REFRESH_TOKEN)).when(tokenService).accessTokenRefresh(any());
        //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .cookie(refreshToken)
            );
        //then
            resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 토큰재발급성공() throws Exception {
        //given
        final String url = "/api/v1/token";
        final String cookieValue = "some value";
        final Cookie newAccessToken = new Cookie("accessToken", cookieValue);
        final Cookie refreshToken = new Cookie("refreshToken", cookieValue);

        doReturn(newAccessToken).when(tokenService).accessTokenRefresh(any());

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .cookie(refreshToken)
        );
        //then
        resultActions.andExpect(status().isOk());
    }
}

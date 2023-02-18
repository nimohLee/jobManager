package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.commons.GlobalExceptionHandler;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TokenControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    protected MockHttpServletRequest request;


    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).setControllerAdvice(new GlobalExceptionHandler()).build();
        request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void 토큰재발급실패_유효하지않은refresh토큰() throws Exception {
        //given
            final String url = "/api/v1/token";
            String refreshToken;
            Cookie[] cookies = request.getCookies();
            List<Cookie> refreshTokenList = Arrays.stream(cookies).filter(i -> i.getName().equals("refreshToken")).collect(Collectors.toList());
            if ( refreshTokenList.size() > 0)
                refreshToken = String.valueOf(refreshTokenList.get(0));
            else refreshToken = null;
            Mockito.doReturn(false).when(jwtTokenProvider).validateToken(refreshToken);
        //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url));
        //then
            resultActions.andExpect(status().isBadRequest());
    }
}

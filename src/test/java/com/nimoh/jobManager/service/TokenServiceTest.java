package com.nimoh.jobManager.service;

import com.nimoh.jobManager.commons.cookie.CookieProvider;
import com.nimoh.jobManager.commons.token.TokenErrorResult;
import com.nimoh.jobManager.commons.token.TokenException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.service.token.TokenService;
import com.nimoh.jobManager.service.token.TokenServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CookieProvider cookieProvider;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    void access토큰발급실패_유효하지않은refresh토큰() {
        //given
        Cookie cookie = new Cookie("refreshToken", "12345");
        Cookie[] cookies = {cookie};
        doReturn(cookie).when(cookieProvider).getCookieForName(any(), any());
        doReturn(false).when(jwtTokenProvider).validateToken(any());
        //when
        final TokenException result = assertThrows(TokenException.class, () -> tokenService.accessTokenRefresh(cookies));
        //then
        assertThat(result.getErrorResult()).isEqualTo(TokenErrorResult.INVALID_REFRESH_TOKEN);
    }
}

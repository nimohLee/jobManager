package com.nimoh.jobManager.commons.cookie;

import com.nimoh.jobManager.exception.token.TokenErrorResult;
import com.nimoh.jobManager.exception.token.TokenException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CookieProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public Cookie getCookieForName(Cookie[] cookies, String cookieName){
        List<Cookie> cookieList = Arrays.stream(cookies).filter(i -> i.getName().equals(cookieName)).collect(Collectors.toList());
        if (cookieList.isEmpty()){
            throw new TokenException(TokenErrorResult.REFRESH_TOKEN_IS_NULL);
        }
        return cookieList.get(0);
    }

    public void setRefreshTokenCookie(Cookie refreshTokenCookie){
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge((int) jwtTokenProvider.getRefreshTokenValidTime());
    }

    public void setAccessTokenCookie(Cookie accessTokenCookie) {
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) jwtTokenProvider.getTokenValidTime());
    }
}

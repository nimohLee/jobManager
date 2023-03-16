package com.nimoh.jobManager.service.token;

import com.nimoh.jobManager.commons.cookie.CookieProvider;
import com.nimoh.jobManager.exception.token.TokenErrorResult;
import com.nimoh.jobManager.exception.token.TokenException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;

    @Override
    public Cookie accessTokenRefresh(Cookie[] cookies) {
        Cookie refreshTokenCookie = cookieProvider.getCookieForName(cookies, "refreshToken");
        boolean isTokenValid = jwtTokenProvider.validateToken(refreshTokenCookie.getValue());
        if (isTokenValid) {
            String refreshToken = refreshTokenCookie.getValue();
            String newAccessToken = jwtTokenProvider.createToken(jwtTokenProvider.getUserPk(refreshToken), jwtTokenProvider.getUserRoles(refreshToken));
            Cookie accessNewTokenCookie = new Cookie("accessToken", newAccessToken);
            cookieProvider.setAccessTokenCookie(accessNewTokenCookie);
            return accessNewTokenCookie;
        } else {
            throw new TokenException(TokenErrorResult.INVALID_REFRESH_TOKEN);
        }
    }
}

package com.nimoh.jobManager.service.token;

import com.nimoh.jobManager.commons.cookie.CookieProvider;
import com.nimoh.jobManager.exception.token.TokenErrorResult;
import com.nimoh.jobManager.exception.token.TokenException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

/**
 * 서비스 - 토큰 관련
 *
 * @author nimoh
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;

    /**
     * access 토큰 재발급
     *
     * @param cookies 재발급할 토큰이 담긴 쿠키
     * @return 새로운 토큰을 쿠키로 반환
     * @throws TokenException Refresh 토큰이 유효하지 않은 경우 예외 발생
     */
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

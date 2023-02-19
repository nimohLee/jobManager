package com.nimoh.jobManager.service.token;

import com.nimoh.jobManager.commons.cookie.CookieProvider;
import com.nimoh.jobManager.commons.token.TokenErrorResult;
import com.nimoh.jobManager.commons.token.TokenException;
import com.nimoh.jobManager.commons.user.UserErrorResult;
import com.nimoh.jobManager.commons.user.UserException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CookieProvider cookieProvider;
    Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

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

package com.nimoh.jobManager.controller;

import antlr.Token;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nimoh.jobManager.commons.token.TokenErrorResult;
import com.nimoh.jobManager.commons.token.TokenException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final long refreshTokenValidTime = 60 * 60 * 24 * 14 * 1000L;
    @Autowired
    public TokenController(JwtTokenProvider jwtTokenProvider, TokenService tokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenService = tokenService;
    }

    @PostMapping("")
    public ResponseEntity<Void> tokenRefresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken;
        Cookie[] cookies = request.getCookies();
        List<Cookie> refreshTokenList = Arrays.stream(cookies).filter(i -> i.getName().equals("refreshToken")).collect(Collectors.toList());

        if ( refreshTokenList.size() > 0){
            refreshToken = String.valueOf(refreshTokenList.get(0));
            String result = tokenService.refreshToken(refreshToken);
            Cookie cookie = new Cookie("refreshToken", result);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) refreshTokenValidTime);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok().build();
        }
        else throw new TokenException(TokenErrorResult.REFRESH_TOKEN_IS_NULL);

    }
}

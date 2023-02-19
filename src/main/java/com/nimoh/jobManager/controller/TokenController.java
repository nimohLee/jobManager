package com.nimoh.jobManager.controller;

import antlr.Token;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nimoh.jobManager.commons.cookie.CookieProvider;
import com.nimoh.jobManager.commons.token.TokenErrorResult;
import com.nimoh.jobManager.commons.token.TokenException;
import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final CookieProvider cookieProvider;

    Logger logger = LoggerFactory.getLogger(TokenController.class);

    @PostMapping("")
    public ResponseEntity<Void> tokenRefresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Cookie newAccessTokenCookie = tokenService.accessTokenRefresh(request.getCookies());
        response.addCookie(newAccessTokenCookie);
        return ResponseEntity.ok().build();
    }
}

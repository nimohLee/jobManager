package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.service.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 토큰 관련 컨트롤러
 *
 * @author nimoh
 */
@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    /**
     * 토큰 재발급
     *
     * @return 성공 시 Http Status code 201
     */
    @Operation(summary = "토큰", description = "토큰을 재발급 합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급에 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 새로고침 토큰입니다"),
            }
    )
    @PostMapping("")
    public ResponseEntity<Void> tokenRefresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Cookie newAccessTokenCookie = tokenService.accessTokenRefresh(request.getCookies());
        response.addCookie(newAccessTokenCookie);
        return ResponseEntity.ok().build();
    }


    /**
     * Access Token 유효성 검사
     *
     * @return token 유효 시 true를 담은 ResponseEntity
     */
    @Operation(summary = "토큰", description = "접근 토큰의 유효성을 확인합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "토큰 유효성확인에 성공했습니다 (true = 유효, false = 유효하지 않음)"),
            }
    )
    @GetMapping("")
    public ResponseEntity<Boolean> accessTokenValid(
            HttpServletRequest request
    ) {
        String accessToken = request.getHeader("Authorization").split(" ")[1];
        boolean result = jwtTokenProvider.validateToken(accessToken);
        return ResponseEntity.ok(result);
    }
}

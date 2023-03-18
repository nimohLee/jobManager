package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.commons.cookie.CookieProvider;
import com.nimoh.jobManager.data.dto.user.UserLogInRequest;
import com.nimoh.jobManager.data.dto.user.UserSignUpRequest;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * 사용자 컨트롤러
 *
 * @author nimoh
 */

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    private final long accessTokenValidTime = 60 * 30 * 1000L;

    private final long refreshTokenValidTime = 60 * 60 * 24 * 14 * 1000L;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 회원가입
     *
     * @param userSignUpRequest 회원가입 요청 DTO
     * @return 성공시 Http Status code 201 과 요청 결과가 담긴 DTO
     */
    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다")
            }
    )
    @PostMapping("")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        userService.signUp(userSignUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSignUpRequest.toString());
    }

    /**
     * 회원탈퇴
     *
     * @return 성공 시 Http Status code 204
     */
    @Operation(summary = "회원탈퇴", description = "회원을 탈퇴합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "회원탈퇴에 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "로그인 되어있지 않습니다 (세션 없음)")
            }
    )
    @DeleteMapping("")
    public ResponseEntity<Void> withdrawal(
            @SessionAttribute(name = "sid", required = false) User loginUser
    ) {
        if (loginUser != null) {
            userService.deleteById(loginUser.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 로그인
     *
     * @param userLogInRequest 로그인 요청 DTO
     * @return 성공 시 Http Status code 200
     */
    @Operation(summary = "로그인", description = "로그인을 시도합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "409", description = "아이디가 또는 비밀번호가 잘못되었습니다")
            }
    )
    @PostMapping("login")
    public ResponseEntity<Void> login(
            @RequestBody UserLogInRequest userLogInRequest,
            HttpServletResponse response
    ) {
        Map<String, String> tokens = userService.login(userLogInRequest);
        Cookie accessTokenCookie = new Cookie("accessToken", tokens.get("accessToken"));
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.get("refreshToken"));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) refreshTokenValidTime);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) accessTokenValidTime);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 로그아웃
     *
     * @return 성공 시 Http Status code 204
     */
    @Operation(summary = "로그아웃", description = "로그아웃을 시도합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "로그아웃에 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
            }
    )
    @PostMapping("logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request
    ) {
        request.getSession().invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
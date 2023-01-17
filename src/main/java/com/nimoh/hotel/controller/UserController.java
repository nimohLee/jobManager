package com.nimoh.hotel.controller;

import com.nimoh.hotel.data.dto.user.UserLogInRequest;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.data.dto.user.UserSignUpRequest;
import com.nimoh.hotel.commons.user.UserException;
import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.service.user.UserService;
import com.nimoh.hotel.session.SessionManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.nimoh.hotel.constants.Headers.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private UserService userService;
    private SessionManager sessionManager;
    public UserController(UserService userService, SessionManager sessionManager){
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",description = "회원가입 성공하였습니다"),
                    @ApiResponse(responseCode = "400",description = "요청값이 잘못되었습니다")
            }
    )
    @PostMapping("")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest
    ) {
            userService.signUp(userSignUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSignUpRequest.toString());
    }

    @Operation(summary = "회원탈퇴", description = "회원을 탈퇴합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204",description = "회원탈퇴에 성공하였습니다"),
                    @ApiResponse(responseCode = "400",description = "요청값이 잘못되었습니다")
            }
    )
    @DeleteMapping("")
    public ResponseEntity<Void> withdrawal(
            @SessionAttribute(name = "sid", required = false) User loginUser,
            HttpServletRequest request
    ) {
            userService.deleteById(loginUser.getId());
            return ResponseEntity.noContent().build();
    }

    @Operation(summary = "로그인", description = "로그인을 시도합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "로그인에 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "409",description = "아이디가 또는 비밀번호가 잘못되었습니다")
            }
    )
    @PostMapping("login")
    public ResponseEntity<UserResponse> login(
            @RequestBody UserLogInRequest userLogInRequest,
            HttpServletResponse response,
            HttpServletRequest request
            ) {
        UserResponse result = userService.login(userLogInRequest);
        request.getSession(true);
        return ResponseEntity.status(HttpStatus.OK).body(result);
            }

    @Operation(summary = "로그인", description = "로그인을 시도합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "로그인에 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "409",description = "아이디가 또는 비밀번호가 잘못되었습니다")
            }
    )
    @PostMapping("logout")
    public ResponseEntity<Void> logout(
            @RequestBody UserLogInRequest userLogInRequest,
            HttpServletRequest request
    ) {
        request.getSession().invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

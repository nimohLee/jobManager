package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.data.dto.user.UserLogInRequest;
import com.nimoh.jobManager.data.dto.user.UserResponse;
import com.nimoh.jobManager.data.dto.user.UserSignUpRequest;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.service.user.UserService;
import com.sun.xml.bind.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 사용자 컨트롤러
 * @author nimoh
 */

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);



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
                    @ApiResponse(responseCode = "400",description = "로그인 되어있지 않습니다 (세션 없음)")
            }
    )
    @DeleteMapping("")
    public ResponseEntity<Void> withdrawal(
            @SessionAttribute(name = "sid", required = false) User loginUser
    ) {
            if(loginUser != null){
                userService.deleteById(loginUser.getId());
                return ResponseEntity.noContent().build();
            }else{
                return ResponseEntity.badRequest().build();
            }




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
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        UserResponse result = userService.login(userLogInRequest);
        HttpSession session = request.getSession();
        session.setAttribute("sid",result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
            }

    @Operation(summary = "로그아웃", description = "로그아웃을 시도합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204",description = "로그아웃에 성공하였습니다"),
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

    @Operation(summary = "세션유무", description = "현재 로그인 세션이 있는 지 유무를 반환합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "현재 세션이 있습니다 (true) "),
                    @ApiResponse(responseCode = "204", description = "현재 세션이 없습니다 (false) "),
            }
    )
    @GetMapping("session")
    public ResponseEntity<Boolean> getSession(
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("sid");
        if (attribute==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(false);
        else return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
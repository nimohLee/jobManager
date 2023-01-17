package com.nimoh.hotel.controller;

import com.nimoh.hotel.data.dto.user.UserLogInRequest;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.data.dto.user.UserSignUpRequest;
import com.nimoh.hotel.commons.user.UserException;
import com.nimoh.hotel.service.user.UserService;
import com.nimoh.hotel.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        try{
            userService.signUp(userSignUpRequest);
        }catch (UserException ue){
            return ResponseEntity.status(ue.getErrorResult().getHttpStatus()).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userSignUpRequest.toString());
    }

    @DeleteMapping("")
    public ResponseEntity<Void> withdrawal(
            @RequestHeader(USER_ID_HEADER) final Long userId
    ) {
            userService.deleteById(userId);
            return ResponseEntity.noContent().build();
    }

    @PostMapping("login")
    public ResponseEntity<UserResponse> login(
            @RequestBody UserLogInRequest request,
            HttpServletResponse response
            ) {
        UserResponse result = userService.login(request);
        sessionManager.createSession(result.getId(),response);
        return ResponseEntity.status(HttpStatus.OK).body(result);
            }
}

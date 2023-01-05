package com.nimoh.hotel.controller;

import com.nimoh.hotel.dto.user.UserSignUpRequest;
import com.nimoh.hotel.errors.UserException;
import com.nimoh.hotel.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        try{
            userService.signUp(userSignUpRequest);
        }catch (UserException ue){
            return ResponseEntity.status(ue.getErrorResult().getStatus()).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userSignUpRequest.toString());


    }
}

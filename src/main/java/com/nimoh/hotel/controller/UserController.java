package com.nimoh.hotel.controller;

import com.nimoh.hotel.dto.user.UserSignUpRequest;
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
    @PostMapping("")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(userSignUpRequest.toString());
    }
}

package com.nimoh.hotel.controller;

import com.nimoh.hotel.constants.Headers;
import com.nimoh.hotel.dto.user.UserSignUpRequest;
import com.nimoh.hotel.errors.user.UserException;
import com.nimoh.hotel.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.nimoh.hotel.constants.Headers.*;

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

    @DeleteMapping("")
    public ResponseEntity<Void> withdrawal(
            @RequestHeader(USER_ID_HEADER) final Long userId
    ) {
            userService.deleteById(userId);
            return ResponseEntity.noContent().build();
    }
}

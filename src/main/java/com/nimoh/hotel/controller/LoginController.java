package com.nimoh.hotel.controller;

import com.nimoh.hotel.domain.User;
import com.nimoh.hotel.service.auth.LoginService;
import com.nimoh.hotel.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
//    private final LoginService loginService;
//    private final SessionManager sessionManager;
//
//    @Autowired
//    public LoginController(LoginService loginService, SessionManager sessionManager) {
//        this.loginService = loginService;
//        this.sessionManager = sessionManager;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Void> login(LoginRequest loginRequest,HttpServletResponse response){
//
//        User loginUser = loginService.login(loginRequest.getLoginId(), loginRequest.getPassword());
//
//        if (loginUser == null){
//            // 아이디 또는 비밀번호 에러 400 리턴
//        }
//        // 로그인 성공 처리
//        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터를 보관
//        sessionManager.createSession(loginUser, response);
//        return null;
//    }
}

package com.nimoh.jobManager.service.token;

import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public TokenServiceImpl(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String refreshToken(String refreshToken) {

        return null;
    }
}

package com.nimoh.jobManager.service.user;

import com.nimoh.jobManager.data.dto.user.UserLogInRequest;
import com.nimoh.jobManager.data.dto.user.UserResponse;
import com.nimoh.jobManager.data.dto.user.UserSignUpRequest;

import java.util.Map;

public interface UserService {
    public UserResponse signUp(UserSignUpRequest request);

    public boolean deleteById(Long userId);

    public Map<String, String> login(UserLogInRequest request);
}

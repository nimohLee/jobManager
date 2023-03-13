package com.nimoh.jobManager.service.user;

import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.data.dto.user.UserLogInRequest;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.data.dto.user.UserResponse;
import com.nimoh.jobManager.data.dto.user.UserSignUpRequest;
import com.nimoh.jobManager.exception.user.UserErrorResult;
import com.nimoh.jobManager.exception.user.UserException;
import com.nimoh.jobManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 유저 서비스 구현체
 *
 * @author nimoh
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserResponse signUp(UserSignUpRequest request) {
        Optional<User> duplicateResult = userRepository.findByUid(request.getUid());
        if (duplicateResult.isPresent()) {
            throw new UserException(UserErrorResult.DUPLICATED_USER_ID);
        }

        String encodedPw = passwordEncoder.encode(request.getPassword());
        User signUpUser = User.builder()
                .uid(request.getUid())
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPw)
                .roles("ROLE_USER")
                .build();
        User result = userRepository.save(signUpUser);
        return UserResponse.builder()
                .uid(result.getUid())
                .name(result.getName())
                .email(result.getEmail())
                .build();
    }

    @Override
    public boolean deleteById(Long userId) {
        userRepository.deleteById(userId);
        return true;
    }

    @Override
    public Map<String, String> login(UserLogInRequest request) {
        User findUser = userRepository.findByUid(request.getUid())
                .orElseThrow(() -> new UserException((UserErrorResult.USER_NOT_FOUND)));
        String encodedPw = findUser.getPassword();
        if (passwordEncoder.matches(request.getPassword(), encodedPw)) {
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles()));
            tokens.put("refreshToken", jwtTokenProvider.createRefreshToken(findUser.getUsername(),findUser.getRoles()));
            return tokens;
        } else {
            throw new UserException(UserErrorResult.WRONG_PASSWORD);
        }
    }
}

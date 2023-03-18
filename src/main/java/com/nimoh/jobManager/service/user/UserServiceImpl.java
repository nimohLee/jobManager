package com.nimoh.jobManager.service.user;

import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.data.dto.user.UserLogInRequest;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.data.dto.user.UserResponse;
import com.nimoh.jobManager.data.dto.user.UserSignUpRequest;
import com.nimoh.jobManager.exception.user.UserErrorResult;
import com.nimoh.jobManager.exception.user.UserException;
import com.nimoh.jobManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 서비스 - 유저 CRUD 서비스
 *
 * @author nimoh
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     *
     * @param request 회원 가입 정보 DTO
     * @return 회원 가입 성공 시 UserResponse 객체
     * @throws UserException 회원 가입 하고자 하는 아이디가 이미 존재하는 경우 예외 발생
     */
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

    /**
     * 회원 탈퇴
     *
     * @param userId 탈퇴할 유저 PK
     * @return 성공 시 true
     * @throws UserException 해당 PK의 유저가 존재하지 않을 시 예외 발생
     */
    @Override
    public boolean deleteById(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorResult.USER_NOT_FOUND));
        userRepository.deleteById(findUser.getId());
        return true;
    }

    /**
     * 유저 로그인
     *
     * @param request 로그인을 위한 UserLogInRequest 객체 (아이디, 비밀번호)
     * @return acessToken과 refreshToken이 담긴 Map
     * @throws UserException 해당 유저가 없을 시 또는 비밀 번호가 틀렸을 시 예외 발생
     */
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

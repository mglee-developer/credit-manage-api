package com.example.credit_manage_api.domain.user.service;

import com.example.credit_manage_api.domain.user.dto.LoginRequest;
import com.example.credit_manage_api.domain.user.dto.LoginResponse;
import com.example.credit_manage_api.domain.user.dto.SignUpRequest;
import com.example.credit_manage_api.domain.user.dto.SignUpResponse;
import com.example.credit_manage_api.domain.user.entity.User;
import com.example.credit_manage_api.domain.user.repository.UserRepository;
import com.example.credit_manage_api.global.exception.ErrorCode;
import com.example.credit_manage_api.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw ErrorCode.EMAIL_ALREADY_EXISTS.toException();
            //throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(User.Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        return new SignUpResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(ErrorCode.USER_NOT_FOUND::toException);

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw ErrorCode.INVALID_PASSWORD.toException();
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token, user.getEmail(), user.getName());
    }

    @Transactional(readOnly = true)
    public SignUpResponse getMe(String email) {
        User user = findByEmail(email);
        return new SignUpResponse(user);
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::toException);
    }
}

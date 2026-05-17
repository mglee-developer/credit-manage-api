package com.example.credit_manage_api.domain.user.controller;

import com.example.credit_manage_api.domain.user.dto.LoginRequest;
import com.example.credit_manage_api.domain.user.dto.LoginResponse;
import com.example.credit_manage_api.domain.user.dto.SignUpRequest;
import com.example.credit_manage_api.domain.user.dto.SignUpResponse;
import com.example.credit_manage_api.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest reqeust) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(reqeust));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest reqeust) {
        return ResponseEntity.ok(userService.login(reqeust));
    }

    @GetMapping("/me")
    public ResponseEntity<SignUpResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getMe(userDetails.getUsername()));

    }
}

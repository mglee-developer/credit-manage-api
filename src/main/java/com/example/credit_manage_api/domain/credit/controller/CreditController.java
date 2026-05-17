package com.example.credit_manage_api.domain.credit.controller;

import com.example.credit_manage_api.domain.credit.dto.CreditHistoryResponse;
import com.example.credit_manage_api.domain.credit.dto.CreditScoreRequest;
import com.example.credit_manage_api.domain.credit.dto.CreditScoreResponse;
import com.example.credit_manage_api.domain.credit.service.CreditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credit")
public class CreditController {
    private final CreditService creditService;

    // 신용점수 등록/수정
    @PostMapping("/score")
    public ResponseEntity<CreditScoreResponse> saveScore(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreditScoreRequest request) {
        return ResponseEntity.ok(creditService.saveScore(userDetails.getUsername(), request));
    }

    // 현재 신용점수 조회
    @GetMapping("/score")
    public ResponseEntity<CreditScoreResponse> getScore(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(creditService.getScore(userDetails.getUsername()));
    }

    // 신용점수 이력 조회
    @GetMapping("/history")
    public ResponseEntity<List<CreditHistoryResponse>> getHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(creditService.getHistory(userDetails.getUsername()));
    }
}

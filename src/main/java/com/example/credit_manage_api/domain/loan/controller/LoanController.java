package com.example.credit_manage_api.domain.loan.controller;

import com.example.credit_manage_api.domain.loan.dto.LoanSimulationRequest;
import com.example.credit_manage_api.domain.loan.dto.LoanSimulationResponse;
import com.example.credit_manage_api.domain.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan")
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/simulate")
    public ResponseEntity<LoanSimulationResponse> simulate(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody LoanSimulationRequest request) {
        return ResponseEntity.ok(
                loanService.simulate(userDetails.getUsername(), request));
    }
}

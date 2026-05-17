package com.example.credit_manage_api.domain.loan.calculator;

import org.springframework.stereotype.Component;

@Component
public class LoanCalculator {
    public long calculateLimit(int creditScore) {
        if (creditScore >= 850) {
            return 50_000_000L;  // 5000만원
        } else if (creditScore >= 750) {
            return 30_000_000L;  // 3000만원
        } else if (creditScore >= 650) {
            return 10_000_000L;  // 1000만원
        } else if (creditScore >= 550) {
            return 5_000_000L;   // 500만원
        } else {
            return 0L;           // 대출 불가
        }
    }

    public double calculateInterestRate(int creditScore) {
        if (creditScore >= 850) {
            return 3.5;   // 3.5%
        } else if (creditScore >= 750) {
            return 5.0;   // 5.0%
        } else if (creditScore >= 650) {
            return 7.5;   // 7.5%
        } else if (creditScore >= 550) {
            return 10.0;  // 10.0%
        } else {
            return 0.0;   // 대출 불가
        }
    }
}

package com.example.credit_manage_api.domain.loan.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoanSimulationResponse {
    private final int creditScore;        // 현재 신용점수
    private final long maxLoanLimit;      // 최대 대출 한도
    private final long requestAmount;     // 대출 희망 금액
    private final double interestRate;    // 금리
    private final long monthlyPayment;    // 월 상환금액
    private final int months;             // 대출 기간
    private final boolean available;      // 대출 가능 여부
    private final String message;         // 안내 메시지
}

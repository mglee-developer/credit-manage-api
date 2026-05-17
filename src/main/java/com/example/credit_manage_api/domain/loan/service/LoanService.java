package com.example.credit_manage_api.domain.loan.service;

import com.example.credit_manage_api.domain.credit.entity.CreditScore;
import com.example.credit_manage_api.domain.credit.repository.CreditScoreRepository;
import com.example.credit_manage_api.domain.loan.calculator.LoanCalculator;
import com.example.credit_manage_api.domain.loan.dto.LoanSimulationRequest;
import com.example.credit_manage_api.domain.loan.dto.LoanSimulationResponse;
import com.example.credit_manage_api.domain.user.entity.User;
import com.example.credit_manage_api.domain.user.repository.UserRepository;
import com.example.credit_manage_api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final UserRepository userRepository;
    private final CreditScoreRepository creditScoreRepository;
    private final LoanCalculator loanCalculator;

    @Transactional(readOnly = true)
    public LoanSimulationResponse simulate(String email, LoanSimulationRequest request) {

        User user = findByEmail(email);

        CreditScore creditScore = creditScoreRepository.findByUserId(user.getId())
                .orElseThrow(ErrorCode.CREDIT_SCORE_NOT_FOUND::toException);

        int score = creditScore.getScore();
        long maxLimit = loanCalculator.calculateLimit(score);
        double interestRate = loanCalculator.calculateInterestRate(score);

        // 대출 불가
        if (maxLimit == 0) {
            return LoanSimulationResponse.builder()
                    .creditScore(score)
                    .maxLoanLimit(0)
                    .requestAmount(request.getRequestAmount())
                    .interestRate(0.0)
                    .monthlyPayment(0)
                    .months(request.getMonths())
                    .available(false)
                    .message("신용점수가 낮아 대출이 불가합니다. (550점 이상 필요)")
                    .build();
        }

        // 희망 금액이 한도 초과
        if (request.getRequestAmount() > maxLimit) {
            return LoanSimulationResponse.builder()
                    .creditScore(score)
                    .maxLoanLimit(maxLimit)
                    .requestAmount(request.getRequestAmount())
                    .interestRate(interestRate)
                    .monthlyPayment(0)
                    .months(request.getMonths())
                    .available(false)
                    .message("희망 금액이 최대 한도를 초과합니다.")
                    .build();
        }

        // 월 상환금액 계산 (원리금균등상환)
        long monthlyPayment = calculateMonthlyPayment(
                request.getRequestAmount(), interestRate, request.getMonths());

        return LoanSimulationResponse.builder()
                .creditScore(score)
                .maxLoanLimit(maxLimit)
                .requestAmount(request.getRequestAmount())
                .interestRate(interestRate)
                .monthlyPayment(monthlyPayment)
                .months(request.getMonths())
                .available(true)
                .message("대출 가능합니다.")
                .build();
    }

    // 원리금균등상환 계산
    private long calculateMonthlyPayment(long amount, double annualRate, int months) {
        double monthlyRate = annualRate / 100 / 12;
        double payment = amount * monthlyRate
                * Math.pow(1 + monthlyRate, months)
                / (Math.pow(1 + monthlyRate, months) - 1);
        return Math.round(payment);
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::toException);
    }
}

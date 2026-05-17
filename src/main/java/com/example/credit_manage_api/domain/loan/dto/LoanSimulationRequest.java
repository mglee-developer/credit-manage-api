package com.example.credit_manage_api.domain.loan.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoanSimulationRequest {
    @NotNull(message = "대출 희망 금액은 필수입니다.")
    @Min(value = 1_000_000, message = "최소 100만원 이상이어야 합니다.")
    @Max(value = 50_000_000, message = "최대 5000만원 이하여야 합니다.")
    private Long requestAmount;

    @NotNull(message = "대출 기간은 필수입니다.")
    @Min(value = 1, message = "최소 1개월 이상이어야 합니다.")
    @Max(value = 60, message = "최대 60개월 이하여야 합니다.")
    private Integer months;
}

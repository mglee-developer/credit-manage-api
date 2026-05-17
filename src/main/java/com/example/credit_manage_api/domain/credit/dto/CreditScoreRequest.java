package com.example.credit_manage_api.domain.credit.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreditScoreRequest {
    @NotNull(message = "점수는 필수입니다.")
    @Min(value = 0, message = "점수는 0점 이상이어야 합니다.")
    @Max(value = 1000, message = "점수는 1000점 이하여야 합니다.")
    private int score;

    private String reason;
}

package com.example.credit_manage_api.domain.credit.dto;

import com.example.credit_manage_api.domain.credit.entity.CreditScore;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreditScoreResponse {
    private final Long id;
    private final int score;
    private final LocalDateTime updatedAt;

    public CreditScoreResponse(CreditScore creditScore) {
        this.id = creditScore.getId();
        this.score = creditScore.getScore();
        this.updatedAt = creditScore.getUpdatedAt();
    }
}

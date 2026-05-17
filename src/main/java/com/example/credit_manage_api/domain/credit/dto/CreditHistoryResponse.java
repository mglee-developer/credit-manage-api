package com.example.credit_manage_api.domain.credit.dto;

import com.example.credit_manage_api.domain.credit.entity.CreditHistory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreditHistoryResponse {
    private final Long id;
    private final int score;
    private final int changedAmount;
    private final String changeType;
    private final String reason;
    private final LocalDateTime createdAt;

    public CreditHistoryResponse(CreditHistory creditHistory) {
        this.id = creditHistory.getId();
        this.score = creditHistory.getScore();
        this.changedAmount = creditHistory.getChangedAmount();
        this.changeType = creditHistory.getChangeType().name();
        this.reason = creditHistory.getReason();
        this.createdAt = creditHistory.getCreatedAt();
    }
}

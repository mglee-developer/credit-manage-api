package com.example.credit_manage_api.domain.credit.entity;

import com.example.credit_manage_api.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "credit_scores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreditScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    //@PreUpdate    // updateScore에서 직접 세팅하니까 불필요
    public void prePersist() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateScore(int score) {
        this.score = score;
        this.updatedAt = LocalDateTime.now();
    }
}

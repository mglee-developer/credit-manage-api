package com.example.credit_manage_api.domain.credit.repository;

import com.example.credit_manage_api.domain.credit.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    Optional<CreditScore> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}

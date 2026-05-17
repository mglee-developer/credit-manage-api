package com.example.credit_manage_api.domain.credit.repository;

import com.example.credit_manage_api.domain.credit.entity.CreditHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Long> {
    List<CreditHistory> findByUserIdOrderByCreatedAtDesc(Long userId);
}

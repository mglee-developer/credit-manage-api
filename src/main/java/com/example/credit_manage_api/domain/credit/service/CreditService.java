package com.example.credit_manage_api.domain.credit.service;

import com.example.credit_manage_api.domain.credit.dto.CreditHistoryResponse;
import com.example.credit_manage_api.domain.credit.dto.CreditScoreRequest;
import com.example.credit_manage_api.domain.credit.dto.CreditScoreResponse;
import com.example.credit_manage_api.domain.credit.entity.CreditHistory;
import com.example.credit_manage_api.domain.credit.entity.CreditScore;
import com.example.credit_manage_api.domain.credit.repository.CreditHistoryRepository;
import com.example.credit_manage_api.domain.credit.repository.CreditScoreRepository;
import com.example.credit_manage_api.domain.user.entity.User;
import com.example.credit_manage_api.domain.user.repository.UserRepository;
import com.example.credit_manage_api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditScoreRepository creditScoreRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final UserRepository userRepository;

    // 신용점수 등록/수정
    @Transactional
    public CreditScoreResponse saveScore(String email, CreditScoreRequest request) {

        User user = findByEmail(email);

        // 기존 점수 있으면 수정, 없으면 새로 등록
        CreditScore creditScore = creditScoreRepository.findByUserId(user.getId())
                .map(existing -> {
                    int changedAmount = Math.abs(request.getScore() - existing.getScore());
                    CreditHistory.ChangeType changeType = request.getScore() >= existing.getScore()
                            ? CreditHistory.ChangeType.INCREASE
                            : CreditHistory.ChangeType.DECREASE;

                    // 이력 저장
                    creditHistoryRepository.save(CreditHistory.builder()
                            .user(user)
                            .score(request.getScore())
                            .changedAmount(changedAmount)
                            .changeType(changeType)
                            .reason(request.getReason())
                            .build());

                    existing.updateScore(request.getScore());

                    return existing;
                })
                .orElseGet(() -> {
                    // 최초 등록 시 이력 저장
                    creditHistoryRepository.save(CreditHistory.builder()
                            .user(user)
                            .score(request.getScore())
                            .changedAmount(request.getScore())
                            .changeType(CreditHistory.ChangeType.INCREASE)
                            .reason(request.getReason())
                            .build());

                    return CreditScore.builder()
                            .user(user)
                            .score(request.getScore())
                            .build();
                });

        return new CreditScoreResponse(creditScoreRepository.save(creditScore));
    }

    // 현재 신용점수 조회
    @Transactional(readOnly = true)
    public CreditScoreResponse getScore(String email) {

        User user = findByEmail(email);

        CreditScore creditScore = creditScoreRepository.findByUserId(user.getId())
                .orElseThrow(ErrorCode.CREDIT_SCORE_NOT_FOUND::toException);

        return new CreditScoreResponse(creditScore);
    }

    // 신용점수 이력 조회
    @Transactional(readOnly = true)
    public List<CreditHistoryResponse> getHistory(String email) {

        User user = findByEmail(email);

        return creditHistoryRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(CreditHistoryResponse::new)
                .collect(Collectors.toList());
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::toException);
    }
}

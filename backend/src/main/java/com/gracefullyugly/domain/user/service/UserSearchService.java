package com.gracefullyugly.domain.user.service;

import com.gracefullyugly.domain.payment.service.PaymentSearchService;
import com.gracefullyugly.domain.review.service.ReviewSearchService;
import com.gracefullyugly.domain.user.dto.ProfileResponse;
import com.gracefullyugly.domain.user.dto.UserDtoUtil;
import com.gracefullyugly.domain.user.dto.UserResponse;
import com.gracefullyugly.domain.user.entity.User;
import com.gracefullyugly.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSearchService {

    private final PaymentSearchService paymentSearchService;
    private final UserRepository userRepository;
    private final ReviewSearchService reviewSearchService;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + "에 해당하는 사용자가 없습니다."));
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException(nickname + "에 해당하는 사용자가 없습니다."));
    }

    public ProfileResponse getProfile(Long userId) {
        User findUser = findById(userId);

        int reviewCount = reviewSearchService.countByUserId(userId);
        int buyCount = paymentSearchService.getBuyCount(userId);

        return UserDtoUtil.toProfileResponse(findUser, reviewCount, buyCount);
    }

    public UserResponse getUser(Long userId) {
        User findUser = findById(userId);

        return UserDtoUtil.userToUserResponse(findUser);
    }

    public boolean existsByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public boolean existsByNickName(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

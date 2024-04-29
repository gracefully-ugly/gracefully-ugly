package com.gracefullyugly.domain.user.repository;

import com.gracefullyugly.domain.user.entity.User;
import java.util.Optional;

import com.gracefullyugly.domain.user.enumtype.SignUpType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String userLoginId);

    Optional<Object> findByRefreshToken(String refreshToken);
}

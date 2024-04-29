package com.gracefullyugly.domain.user.repository;

import com.gracefullyugly.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String userLoginId);

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}

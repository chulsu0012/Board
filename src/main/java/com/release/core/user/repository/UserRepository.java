package com.release.core.user.repository;

import com.release.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 필요한 추가적인 쿼리 메서드 정의
}

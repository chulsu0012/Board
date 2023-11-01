package com.release.core.repository;

import com.release.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository{
    User save(User member);
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> findAll();
    // 필요한 추가적인 쿼리 메서드 정의
}

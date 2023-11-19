package com.release.core.repository;

import com.release.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository{
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> findAll();

    void deleteUser(User user);

    User updateUser(User user);

    List<User> findAdminUsers();

    List<User> findUsersWithPagination(int page, int pageSize);
    // 필요한 추가적인 쿼리 메서드 정의
}

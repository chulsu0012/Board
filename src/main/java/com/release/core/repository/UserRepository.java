package com.release.core.repository;

import com.release.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserEmail(String userEmail);
    Page<User> findAllByUserNameContains(String userName, PageRequest pageRequest);
    Boolean existsByUserEmail(String userEmail);
    Boolean existsByUserName(String userName);
}

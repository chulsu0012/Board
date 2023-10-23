package com.release.core.user.repository;

import com.release.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 필요한 추가적인 쿼리 메서드 정의
//     사용자 이름으로 조회
//    User findByUsername(String username);
//
//    // 사용자 이메일로 조회 (대소문자 무시)
//    User findByUserEmailIgnoreCase(String email);
//
//    // 사용자 이름과 권한으로 조회
//    List<User> findByUsernameAndUserIsAdmin(String username, int isAdmin);
//
//    // 사용자 등록일이 특정 날짜 이후인 사용자 조회
//    List<User> findByUserRegisterDateAfter(LocalDateTime date);
//
//    // 사용자 이름이 특정 문자열을 포함하는 사용자 조회
//    List<User> findByUsernameLike(String keyword);
//
//    // 사용자 이름으로 시작하는 사용자 수 조회
//    Long countByUsernameStartingWith(String prefix);
//
//    // 사용자 이름으로 삭제
//    void deleteByUsername(String username);
}

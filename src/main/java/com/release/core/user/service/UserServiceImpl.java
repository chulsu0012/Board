package com.release.core.user.service;

import com.release.core.user.model.User;
import com.release.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    User createUser(User user) {
        return null;
    }

    User updateUser(User user) {
        return null;
    }

    void deleteUser(int userId) {

    }

    User getUserById(int userId) {
        return null;
    }

    List<User> getAllUsers() {
        return null;
    }
/*
    @Override
    public User createUser(User user) {
        // 사용자 생성 로직 구현
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // 사용자 업데이트 로직 구현
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        // 사용자 삭제 로직 구현
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(int userId) {
        // 사용자 조회 로직 구현
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        // 모든 사용자 목록 조회 로직 구현
        return userRepository.findAll();
    }
 */

}

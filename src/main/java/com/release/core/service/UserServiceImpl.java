package com.release.core.service;

import com.release.core.domain.User;
import com.release.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public class UserServiceImpl implements UserService{


    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /*
    @Override
    @Transactional
    public User createUser(User user) {
        return null;
    }
    */

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAdminUsers() {
        return userRepository.findAdminUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersWithPagination(int page, int pageSize) {
        return userRepository.findUsersWithPagination(page, pageSize);
    }

    /*회원가입*/
    @Override
    public void validateDuplicateUser(User user) {
        userRepository.findByName(user.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        // 회원 가입 기능을 여기에 추가
        return user.getId();
    }

    /*
    @Override
    public String login(String username, String password) {
        // 실제 로그인 로직을 구현
    }

     @Override
public User updateUser(Long userId, User updatedUser) {
    // 사용자 정보 수정 로직을 구현하고 업데이트된 사용자 정보를 반환
}
@Override
public void deleteUser(Long userId) {
    // 사용자 삭제 로직을 구현
}
     */
}

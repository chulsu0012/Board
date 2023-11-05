package com.release.core.service;

import com.release.core.domain.User;
import com.release.core.repository.MemoryUserRepository;
import com.release.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //private final UserRepository userRepository = new
    //       MemoryUserRepository();

    /**
     * 회원가입
     */
    public Long join(User user){
        validateDuplicateUser(user); //중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }
    private void validateDuplicateUser(User user) {
        userRepository.findByName(user.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long memberId) {
        return userRepository.findById(memberId);
    }

}

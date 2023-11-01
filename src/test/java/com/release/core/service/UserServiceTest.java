package com.release.core.service;

import com.release.core.domain.User;
import com.release.core.repository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService;
    MemoryUserRepository userRepository;
    @BeforeEach
    public void beforeEach(){
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }
    //MemoryUserRepository userRepository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        userRepository.clearStore();
    }

    @Test
    void join() {
        // given
        User user = new User();
        user.setName("hello");
        // when
        Long saveId = userService.join(user);

        // then
        User findUser =  userService.findOne(saveId).get();
        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
//Given
        User user1 = new User();
        user1.setName("spring");
        User user2 = new User();
        user2.setName("spring");
//When
        userService.join(user1);
        // userService.join(user2);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(user2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
    @Test
    void findUsers() {
    }

    @Test
    void findOne() {
    }
}
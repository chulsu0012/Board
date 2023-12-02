package com.release.core.service;

import com.release.core.domain.User;
import com.release.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {
    @Autowired
    UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception {
//Given
        User user = new User();
        user.setName("hello");
        user.setUserEmail("hello@naver.com");
        user.setUserPassword("hellohello");
        user.setUserIsAdmin(0L);
        user.setUserRegisterDate("20231101");
//When
        Long saveId = userService.join(user);
//Then
        User findMember = userRepository.findById(saveId).get();
        assertEquals(user.getName(), findMember.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
//Given
        User user1 = new User();
        user1.setName("spring");
        user1.setUserEmail("hello@naver.com");
        user1.setUserPassword("hellohello");
        user1.setUserIsAdmin(0L);
        user1.setUserRegisterDate("20231101");
        User user2 = new User();
        user2.setName("spring");
        user2.setUserEmail("hello2@naver.com");
        user2.setUserPassword("hello2hello2");
        user2.setUserIsAdmin(0L);
        user2.setUserRegisterDate("202311012");
//When
        userService.join(user1);
        //userService.join(user2);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(user2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}

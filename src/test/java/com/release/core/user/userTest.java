package com.release.core.user;

import com.release.core.user.model.User;
import com.release.core.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
@SpringBootTest
public class userTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserRepository() {
        // 새로운 User 객체 생성
        User user = new User();
        user.setUserIsAdmin(0);
        user.setUserName("John Doe");
        user.setUserEmail("john@example.com");
        user.setUserPassword("password");
        user.setUserRegisterDate(LocalDateTime.now());

        // UserRepository를 통해 데이터베이스에 저장
        userRepository.save(user);

        // UserRepository를 통해 데이터베이스에서 조회
        List<User> users = userRepository.findAll();
        for (User u : users) {
            System.out.println("User ID: " + u.getUserId());
            System.out.println("User Name: " + u.getUserName());
            System.out.println("User Email: " + u.getUserEmail());
            System.out.println("Registration Date: " + u.getUserRegisterDate());
        }
    }
}

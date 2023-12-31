package com.release.core.controller;

import com.release.core.domain.User;
import com.release.core.dto.UserJoinRequest;
import com.release.core.dto.UserLoginRequest;
import com.release.core.repository.UserRepository;
import com.release.core.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()) // Spring Security 초기화
                .build();
    }

    @Test
    void join(){
        // GIVEN
        UserJoinRequest request = new UserJoinRequest();
        request.setUserEmail("user@example.com");
        request.setUserName("John Doe");
        request.setUserPassword("password");
        request.setUserPasswordCheck("password");

        // Perform the join request
        ResponseEntity<String> response = restTemplate.postForEntity("/join", request, String.class);

        // Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("회원가입에 성공했습니다!");

        // Verify that the user is saved in the database
        Optional<User> userOptional = userRepository.findByUserEmail("user@example.com");
        User savedUser = userOptional.orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserName()).isEqualTo("John Doe");

        // Verify that the password is encoded
        assertThat(passwordEncoder.matches("password", savedUser.getUserPassword())).isTrue();
    }
    @Test
    public void login_success() throws Exception{
        // GIVEN
        UserJoinRequest request = new UserJoinRequest();
        request.setUserEmail("user@example.com");
        request.setUserName("John Doe");
        request.setUserPassword("password");
        request.setUserPasswordCheck("password");

        // 회원가입을 먼저 수행
        ResponseEntity<String> response = restTemplate.postForEntity("/join", request, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        // 로그인 시도
        String userEmail = "user@example.com";
        String userPassword = "password";

        // 로그인 시도 후, 인증(Authenticated)이 되어야 함
        ResultActions result = mockMvc.perform(post("/login")
                        .param("userEmail", userEmail)
                        .param("userPassword", userPassword))
                .andExpect(status().is3xxRedirection()) // 로그인 후 리다이렉션 발생
                .andExpect(redirectedUrl("/")); // 리다이렉션된 경로 확인

// 로그인 후, SecurityContextHolder를 통해 인증 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication.isAuthenticated()).isTrue();
    }

    @Test
    public void login_fail() throws Exception{
        // 로그인 시도
        String userEmail = "존재하지 않는 아이디";
        String userPassword = "123";

        // 로그인 시도 후, 인증(Authenticated)이 실패해야 함
        mockMvc.perform(formLogin("/login")
                        .user(userEmail)
                        .password(userPassword))
                .andExpect(unauthenticated());
    }
}
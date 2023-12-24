package com.release.core.controller;

import com.release.core.dto.AddUserRequestDTO;
import com.release.core.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc   // MockMvc 생성
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // MockMvc를 설정하여 컨트롤러 테스트를 진행합니다.
        MockitoAnnotations.openMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setViewResolvers(viewResolver).build();
    }

    @DisplayName("testLogin: 로그인에 성공한다")
    @Test
    public void testlogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @DisplayName("testSignupPage: 예상된 뷰가 응답되었다.")
    @Test
    public void testSignupPage() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @DisplayName("testSignup: 회원가입에 성공한다")
    @Test
    public void testSignup() throws Exception {
        // 테스트 데이터 생성
        AddUserRequestDTO requestDTO = new AddUserRequestDTO();
        requestDTO.setEmail("testEmail");
        requestDTO.setPassword("testPassword");

        // userService의 save 메서드가 호출되었을 때의 동작 설정
        doNothing().when(userService).save(requestDTO);

        mockMvc.perform(post("/user")
                        .param("username", "testUser")
                        .param("password", "testPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // userService의 save 메서드가 정확히 한 번 호출되었는지 확인
        verify(userService, times(1)).save(requestDTO);
    }


    @DisplayName("testLogout: 로그아웃에 성공한다")
    @Test
    public void testLogout() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        // SecurityContextHolder에 가짜 SecurityContext 설정
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // SecurityContextLogoutHandler의 logout 메서드 호출 시 동작 설정
        doNothing().when(new SecurityContextLogoutHandler()).logout(request, response, authentication);

        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // SecurityContextLogoutHandler의 logout 메서드가 호출되었는지 확인
        verify(new SecurityContextLogoutHandler()).logout(request, response, authentication);
    }
}
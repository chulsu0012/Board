package com.release.core;

import com.release.core.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final UserDetailService userService;

    // (1) 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // (2) 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()    // (3) 인증, 인가 설정
                .requestMatchers("/login", "/signup", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/login")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )   // (6) csrf 비활성화
                .build();
    }

    // (7) 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth,
                                                       UserDetailService userDetailService,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder);

        return auth.build();
    }


    // (9) 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

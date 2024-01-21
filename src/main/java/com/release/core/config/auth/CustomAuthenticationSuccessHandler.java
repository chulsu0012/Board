package com.release.core.config.auth;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
public class CustomAuthenticationSuccessHandler {
    /*
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 사용자 정보에서 필요한 정보를 추출합니다.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 사용자 정보에서 userId를 가져와 세션에 설정
        YourUserDetails userDetails = (YourUserDetails) authentication.getPrincipal();
        session.setAttribute("userId", userDetails.getUserId());
        super.onAuthenticationSuccess(request, response, authentication);
    }

     */
}

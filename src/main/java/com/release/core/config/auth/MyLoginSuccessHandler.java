package com.release.core.config.auth;

import com.release.core.domain.User;
import com.release.core.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        User loginUser = userRepository.findByUserEmail(authentication.getName()).get();

        // 성공 시 메세지 출력 후 홈 화면으로 redirect
        //response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            pw.println("<script>alert('hello! " + loginUser.getUserName() + "'); ");
            pw.println("location.href='" + prevPage + "';</script>");
        } else {
            pw.println("<script>alert('hello! " + loginUser.getUserName() + "'); ");
            pw.println("location.href='/';</script>");
        }
        pw.flush();
    }

}

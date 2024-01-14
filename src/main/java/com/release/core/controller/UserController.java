package com.release.core.controller;

import com.release.core.domain.User;
import com.release.core.dto.UserDto;
import com.release.core.dto.UserJoinRequest;
import com.release.core.dto.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.release.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    private static final Logger log = LogManager.getLogger(UserController.class);

    @GetMapping("/")
    public String home() {
        log.info("This is home page.");
        // 여기에서 home.html을 표시하도록 설정합니다.
        return "home";
    }
    // 회원가입
    @GetMapping("/join")
    public String joinPage(Model model) {
        log.info("This is join page.");
        model.addAttribute("userJoinRequest", new UserJoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserJoinRequest req,
                       BindingResult bindingResult, Model model) {

        // Validation
        if (userService.joinValid(req, bindingResult).hasErrors()) {
            return "join";
        }

        userService.join(req);
        model.addAttribute("message", "회원가입에 성공했습니다!\nJoin success\n로그인 후 사용 가능합니다!");
        model.addAttribute("nextUrl", "login");
        return "printMessage";
    }

    // 로그인
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        log.info("This is login page.");
        // 로그인 성공 시 이전 페이지로 redirect 되게 하기 위해 세션에 저장
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login") && !uri.contains("/join")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginRequest req,
                        BindingResult bindingResult, HttpServletRequest httpServletRequest,
                        Model model) {

        log.info("before login");
        model.addAttribute("loginType", "login");
        model.addAttribute("pageName", "로그인");


        User user = userService.login(req, httpServletRequest);
        log.info("로그인 성공, User: " + user.getUserEmail() + ", " + user.getUserName());   // 출력됨

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if (user == null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        } else {
            model.addAttribute("message", "로그인에 성공했습니다!\n login success msg");
            model.addAttribute("nextUrl", "home");
            return "printMessage";
        }


        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "로그인 실패!\nlogin fail message");
            model.addAttribute("nextUrl", "home");
            return "printMessage";
        }

        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
        // 세션에 userId를 넣어줌
        session.setAttribute("userId", user.getUserId());
        session.setMaxInactiveInterval(1800); // Session이 1시간동안 유지

        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; // 로그아웃 후 로그인 페이지로 리다이렉트

        /*
        model.addAttribute("loginType", "login");
        model.addAttribute("pageName", "로그인");

        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/login";
         */
    }

    @GetMapping("/user")
    public String getUserInfo(Model model) {
        // 현재 로그인한 사용자의 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보가 세션에 저장되어 있으므로, 사용자 이름을 가져올 수 있습니다.
        String userName = authentication.getName();

        log.info("Current login user: " + userName);

        // 모델에 사용자 이름을 추가하여 템플릿에 전달합니다.
        model.addAttribute("userName", userName);

        // user.html 템플릿을 반환합니다.
        return "user";
    }

    /*
    public ResponseEntity<Map<String, String>> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Map<String, String> responseData = new HashMap<>();
        responseData.put("userName", userName);
        log.info("Current login user: " + userName);

        return ResponseEntity.ok(responseData);
    }

     */

    /*
    public String getUserInfo() {
        // 현재 로그인한 사용자의 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보가 세션에 저장되어 있으므로, 사용자 이름을 가져올 수 있습니다.
        String userName = authentication.getName();

        log.info("Current login user: " + userName);
        // 사용자 이름을 반환합니다.
        return "redirect:/";
    }

     */

    // 정보 수정
    @GetMapping("/edit")
    public String userEditPage(Authentication auth, Model model) {
        User user = userService.myInfo(auth.getName());
        model.addAttribute("userDto", UserDto.of(user));
        return "users/edit";
    }

    @PostMapping("/edit")
    public String userEdit(@Valid @ModelAttribute UserDto dto, BindingResult bindingResult,
                           Authentication auth, Model model) {

        // Validation
        if (userService.editValid(dto, bindingResult, auth.getName()).hasErrors()) {
            return "users/edit";
        }

        userService.edit(dto, auth.getName());

        model.addAttribute("message", "정보가 수정되었습니다.");
        model.addAttribute("nextUrl", "/users/myPage");
        return "printMessage";
    }

    // 회원 탈퇴
    @GetMapping("/delete")
    public String userDeletePage(Authentication auth, Model model) {
        User user = userService.myInfo(auth.getName());
        model.addAttribute("userDto", UserDto.of(user));
        return "users/delete";
    }

    @PostMapping("/delete")
    public String userDelete(@ModelAttribute UserDto dto, Authentication auth, Model model) {
        Boolean deleteSuccess = userService.delete(auth.getName(), dto.getNowUserPassword());
        if (deleteSuccess) {
            model.addAttribute("message", "탈퇴 되었습니다.");
            model.addAttribute("nextUrl", "/users/logout");
            return "printMessage";
        } else {
            model.addAttribute("message", "현재 비밀번호가 틀려 탈퇴에 실패하였습니다.");
            model.addAttribute("nextUrl", "/users/delete");
            return "printMessage";
        }
    }

    @GetMapping("/admin")
    public String adminPage(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "") String keyword, Model model) {

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<User> users = userService.findAllByUserName(keyword, pageRequest);

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "users/admin";
    }

}

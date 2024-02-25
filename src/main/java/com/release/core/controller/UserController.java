package com.release.core.controller;

import com.release.core.domain.User;
import com.release.core.dto.*;

import com.release.core.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.release.core.service.UserService;
import lombok.RequiredArgsConstructor;
import com.release.core.config.auth.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final UserDetailService userDetailService;
    private final UserRepository userRepository;

    private static final Logger log = LogManager.getLogger(UserController.class);


    // 회원가입
    @PostMapping("join")
    public ResponseEntity<UserJoinResponse> joinUser(@Valid @RequestBody UserJoinRequest req,
                                                     BindingResult bindingResult) {

        // Validation
        // userEmail
        if (userService.joinValid(req, bindingResult).hasErrors()) {
            if(req.getUserEmail().isEmpty()){
                return ResponseEntity.badRequest().body(new UserJoinResponse("이메일이 비어있습니다. 이메일 주소를 입력해주세요."));
            }
            else if(userRepository.existsByUserEmail(req.getUserEmail())){
                return ResponseEntity.badRequest().body(new UserJoinResponse("이미 사용 중인 이메일 주소입니다. 다른 이메일 주소를 입력해주세요."));
            }
        }

        // userPassword
        if(req.getUserPassword().isEmpty()){
            return ResponseEntity.badRequest().body(new UserJoinResponse("비밀번호가 비어있습니다."));
        }
        if(!req.getUserPassword().equals(req.getUserPasswordCheck())){
            return ResponseEntity.badRequest().body(new UserJoinResponse("비밀번호 확인이 일치하지 않습니다."));
        }

        userService.join(req);

        UserJoinResponse response = new UserJoinResponse("회원가입에 성공했습니다!", req.getUserName(), req.getUserEmail());
        return ResponseEntity.ok(response);
        //return ResponseEntity.ok("회원가입에 성공했습니다!");
        //return new UserJoinResponse("회원가입에 성공했습니다!", req.getUserName(), req.getUserEmail());
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<UserLoginResponse> loginUser(@Valid @RequestBody UserLoginRequest req,
                                                       BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        try {
            User user = userService.login(req, httpServletRequest);
            log.info("로그인 성공, User: " + user.getUserEmail() + ", " + user.getUserName());

            // 성공적으로 로그인한 경우
            httpServletRequest.getSession().invalidate();
            // Spring Security 컨텍스트에 인증 정보를 저장
            UserDetails userDetails = userDetailService.loadUserByUsername(req.getUserEmail());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 사용자 정보를 세션에 저장
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("userId", user.getUserId());
            session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

            // 인증 확인
            if (authentication != null && authentication.isAuthenticated()) {
                UserLoginResponse response = new UserLoginResponse("사용자가 인증되었습니다", user.getUserId());
                return ResponseEntity.ok(response);
            } else {
                UserLoginResponse response = new UserLoginResponse("사용자가 인증되지 않았습니다", user.getUserId());
                return ResponseEntity.ok(response);
            }
        } catch (UsernameNotFoundException ex) {
            // 로그인 실패 시 예외 처리
            return ResponseEntity.badRequest().body(new UserLoginResponse(ex.getMessage()));
        }
    }

    // 로그아웃
    @PostMapping("logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            if(session != null){
                // 세션을 무효화하고 세션 관련 데이터를 삭제
                session.invalidate();
            }
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return ResponseEntity.ok("로그아웃 되었습니다.");
        }

        if (session != null) {
            // 세션을 무효화하고 세션 관련 데이터를 삭제
            session.invalidate();
            return ResponseEntity.ok("로그아웃 되었습니다.");
        } else {
            // 세션이 없는 경우, 이미 로그아웃 상태라고 간주
            return ResponseEntity.ok("이미 로그아웃 되었습니다.");
        }
    }

    // 내 정보
    @GetMapping("info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@SessionAttribute(name="userId") Long sauserId, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null) {
            Long currentUserId = (Long)session.getAttribute("userId");
            if(currentUserId != null) {
                log.info("currentUserId = " + currentUserId);
                Optional<User> optionalCurrentUser = userRepository.findByUserId(currentUserId);
                if (optionalCurrentUser.isPresent()) {
                    User currentUser = optionalCurrentUser.get();
                    UserInfoResponse userInfoResponse = new UserInfoResponse(
                            sauserId,
                            currentUser.getUserName(),
                            currentUser.getUserEmail(),
                            currentUser.getUserRegisterDate(),
                            String.valueOf(currentUser.getUserRole())
                    );
                    return ResponseEntity.ok(userInfoResponse);
                }
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // 유저 이름 변경 요청 처리
    @PutMapping("/editUserName")
    public ResponseEntity<String> editUserName(@SessionAttribute(name="userId") Long userId, HttpServletRequest httpServletRequest, @RequestParam String newUserName) {
        try {
            HttpSession session = httpServletRequest.getSession(false);
            //Long userId = (Long)session.getAttribute("userId");
            userService.editUName(userId, newUserName);
            return ResponseEntity.ok("유저 이름이 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 이름 변경에 실패하였습니다.");
        }
    }

    @PutMapping("/editUserPassword")
    public ResponseEntity<String> editUserPassword(HttpServletRequest httpServletRequest, @RequestParam String newUserPassword, @RequestParam String newUserPasswordCheck) {
        try {
            HttpSession session = httpServletRequest.getSession(false);
            Long userId = (Long)session.getAttribute("userId");
            userService.editUPassword(userId, newUserPassword, newUserPasswordCheck);
            if (!newUserPassword.equals(newUserPasswordCheck)) {
                return ResponseEntity.badRequest().body("새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 이름 변경에 실패하였습니다.");
        }
    }

    // 회원 탈퇴
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest httpServletRequest, @RequestParam String checkUserPassword) {
        try {
            HttpSession session = httpServletRequest.getSession(false);
            Long userId = (Long)session.getAttribute("userId");
            // userId를 사용하여 회원 정보 삭제 로직 수행
            if(userService.deleteUser(userId, checkUserPassword)){
                return ResponseEntity.ok("회원 정보가 삭제되었습니다.");
            }else{
                return ResponseEntity.ok("확인 비밀번호가 올바르지 않습니다.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 삭제에 실패하였습니다.");
        }
    }



    /*
    // 정보 수정
    @PutMapping("/edit")
    public ResponseEntity<String> userEdit(@SessionAttribute(name="userId") Long userId, BindingResult bindingResult,
                           Authentication auth, UserDTO dto) {

        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(userOptional.isPresent()){
            userService.edit(userOptional.get(), dto)
        }

        if (userService.editValid(dto, bindingResult, auth.getName()).hasErrors()) {
            return "users/edit";
        }

        userService.edit(dto, auth.getName());

    }
    */




/*
    @GetMapping("/admin")
    public String adminPage(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "") String keyword, Model model) {

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<User> users = userService.findAllByUserName(keyword, pageRequest);

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "users/admin";
    }
    */

}

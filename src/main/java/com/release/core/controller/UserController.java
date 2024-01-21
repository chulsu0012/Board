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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final UserRepository userRepository;

    private static final Logger log = LogManager.getLogger(UserController.class);


    // 회원가입
    @PostMapping("join")
    public ResponseEntity<UserJoinResponse> joinUser(@Valid @RequestBody UserJoinRequest req,
                                                     BindingResult bindingResult) {

        // Validation
        if (userService.joinValid(req, bindingResult).hasErrors()) {
            return ResponseEntity.badRequest().body(new UserJoinResponse("회원가입에 실패했습니다."));
            //return ResponseEntity.badRequest().body("회원가입에 실패했습니다.");
            //return new UserJoinResponse("회원가입에 실패했습니다.");
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

        User user = userService.login(req, httpServletRequest);
        log.info("로그인 성공, User: " + user.getUserEmail() + ", " + user.getUserName());   // 출력됨

        if (bindingResult.hasErrors()) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if (user == null) {
            return ResponseEntity.badRequest().body(new UserLoginResponse("로그인 아이디 또는 비밀번호가 틀렸습니다."));
            //return ResponseEntity.badRequest().body("로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        UserLoginResponse response = new UserLoginResponse("로그인에 성공했습니다!", user.getUserId());
        return ResponseEntity.ok(response);
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
    public ResponseEntity<UserInfoResponse> getUserInfo(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null) {
            Long currentUserId = (Long)session.getAttribute("userId");
            if(currentUserId != null) {
                log.info("currentUserId = " + currentUserId);
                Optional<User> optionalCurrentUser = userRepository.findByUserId(currentUserId);
                if (optionalCurrentUser.isPresent()) {
                    User currentUser = optionalCurrentUser.get();
                    UserInfoResponse userInfoResponse = new UserInfoResponse(
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
    public ResponseEntity<String> editUserName(HttpServletRequest httpServletRequest, @RequestParam String newUserName) {
        try {
            HttpSession session = httpServletRequest.getSession(false);
            Long userId = (Long)session.getAttribute("userId");
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
    public ResponseEntity<String> deleteUser(@SessionAttribute(name = "userId") Long userId, @RequestParam String checkUserPassword) {
        try {
            // userId를 사용하여 회원 정보 삭제 로직 수행
            if(userService.deleteUser(userId, checkUserPassword)){
                return ResponseEntity.ok("회원 정보가 삭제되었습니다.");
            }else{
                return ResponseEntity.ok("회원 탈퇴에 실패했습니다.");
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

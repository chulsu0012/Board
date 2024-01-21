package com.release.core.service;

import com.release.core.domain.User;
import com.release.core.dto.UserDTO;
import com.release.core.dto.UserJoinRequest;
import com.release.core.dto.UserLoginRequest;
import com.release.core.repository.UserRepository;
import com.release.core.config.auth.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.security.core.userdetails.UserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserDetailService userDetailService;

    private final BCryptPasswordEncoder encoder;

    private static final Logger log = LogManager.getLogger(UserService.class);

    // 회원가입 유효
    public BindingResult joinValid(UserJoinRequest req, BindingResult bindingResult){
        // userEmail
        if(req.getUserEmail().isEmpty()){
            bindingResult.addError(new FieldError("req", "userEmail", "이메일이 비어있습니다."));
        }
        else if(userRepository.existsByUserEmail(req.getUserEmail())){
            bindingResult.addError(new FieldError("req", "userEmail", "아이디가 중복입니다."));
        }

        // userPassword
        if(req.getUserPassword().isEmpty()){
            bindingResult.addError(new FieldError("req", "userPassword", "비밀번호가 비어있습니다."));
        }
        if(!req.getUserPassword().equals(req.getUserPasswordCheck())){
            bindingResult.addError(new FieldError("req", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        // userName
        if(req.getUserName().isEmpty()){
            bindingResult.addError(new FieldError("req", "userName", "이름이 비어있습니다."));
        }
        else if(userRepository.existsByUserName(req.getUserName())){
            bindingResult.addError(new FieldError("req", "userName", "이름이 중복됩니다."));
        }

        return bindingResult;
    }

    public void join(UserJoinRequest req){
        log.info("Joining :" + req.getUserName() + ", " + req.getUserEmail());
        userRepository.save(req.toEntity(encoder.encode(req.getUserPassword())));
        //userRepository.save(req.toEntity(req.getUserPassword()));
    }

    public User login(UserLoginRequest req, HttpServletRequest httpServletRequest){

        Optional<User> userOptional = userRepository.findByUserEmail(req.getUserEmail());
        User user = userOptional.get();
        log.info("Logining :" + user.getUserName() + ", " + req.getUserEmail());
        if (userOptional.isPresent()) {
            // Optional에서 User 객체를 가져옴


            if (encoder.matches(req.getUserPassword(), user.getUserPassword())) {
                httpServletRequest.getSession().invalidate();
                // Spring Security 컨텍스트에 인증 정보를 저장
                UserDetails userDetails = userDetailService.loadUserByUsername(req.getUserEmail());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 사용자 정보를 세션에 저장
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("userId", user.getUserId());
                //session.setAttribute("user", user);
                session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
                return user;
            } else {
                // 비밀번호가 일치하지 않으면 로그인 실패
                throw new UsernameNotFoundException("비밀번호가 일치하지 않습니다.\nPassword doesn't match.");
            }
        } else {
            // 사용자 정보가 없을 경우 처리 (예: 사용자 없음 예외 처리)
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.\nUser is not found.");
        }
    }

    public User myInfo(String userEmail){
        return userRepository.findByUserEmail(userEmail).get();
    }

    // 정보수정 유효
    public BindingResult editValid(UserDTO dto, BindingResult bindingResult, String userEmail){
        // userPassword
        User loginUser = userRepository.findByUserEmail(userEmail).get();

        if(dto.getNewUserPassword().isEmpty()){
            bindingResult.addError(new FieldError("dto", "nowUserPassword", "현재 비밀번호가 비어있습니다."));
        }
        else if(!encoder.matches(dto.getNowUserPassword(), loginUser.getUserPassword())){
            bindingResult.addError(new FieldError("dto", "newUserPasswordCheck", "비밀번호가 일치하지 않습니다."));
        }

        // userName
        if(dto.getUserName().isEmpty()){
            bindingResult.addError(new FieldError("dto", "userName", "이름이 비어 있습니다."));
        }
        else if(!dto.getUserName().equals(loginUser.getUserName()) && userRepository.existsByUserName(dto.getUserName())){
            bindingResult.addError(new FieldError("dto", "userName", "이름이 중복됩니다."));
        }

        return bindingResult;
    }

    public void editUName(Long userId, String newUserName) {
        //User user = getCurrentUser(); // 현재 로그인된 사용자 가져오기
        Optional<User> OptionalcurrentUser = userRepository.findByUserId(userId);
        if(OptionalcurrentUser.isPresent()) {
            User currentUser = OptionalcurrentUser.get();
            currentUser.setUserName(newUserName);
            userRepository.save(currentUser);
        }
    }

    @Transactional
    public void editUPassword(Long userId, String newUserPassword, String newUserPasswordCheck){
        Optional<User> OptionalcurrentUser = userRepository.findByUserId(userId);
        if(OptionalcurrentUser.isPresent()) {
            User currentUser = OptionalcurrentUser.get();
            String encodedPassword = encoder.encode(newUserPassword);
            currentUser.setUserPassword(encodedPassword);
            userRepository.save(currentUser);
        }

    }

    @Transactional
    public Boolean deleteUser(Long userId, String checkUserPassword) {
        Optional<User> OptionalcurrentUser = userRepository.findByUserId(userId);

        if (OptionalcurrentUser.isPresent()) {
            User currentUser = OptionalcurrentUser.get();
            log.info("userId: " + userId);
            log.info("Current user: " + currentUser.getUserId());
            if (encoder.matches(checkUserPassword, currentUser.getUserPassword())) {
                userRepository.delete(currentUser);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Page<User> findAllByUserName(String keyword, PageRequest pageRequest){
        return userRepository.findAllByUserNameContains(keyword, pageRequest);
    }
}
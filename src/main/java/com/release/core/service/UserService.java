package com.release.core.service;

import com.release.core.config.auth.UserDetail;
import com.release.core.domain.User;
import com.release.core.dto.UserDto;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserDetailService userDetailService;

    private final BCryptPasswordEncoder encoder;

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
        //userRepository.save(req.toEntity(encoder.encode(req.getUserPassword())));
        userRepository.save(req.toEntity(req.getUserPassword()));

    }

    public User login(UserLoginRequest req){

        Optional<User> userOptional = userRepository.findByUserEmail(req.getUserEmail());

<<<<<<< Updated upstream
        UserDetails userDetails = null; //loadUserByUsername(req.getUserEmail());

=======
>>>>>>> Stashed changes
        if (userOptional.isPresent()) {
            // Optional에서 User 객체를 가져옴
            User user = userOptional.get();

            if (encoder.matches(req.getUserPassword(), user.getUserPassword())) {
                // Spring Security 컨텍스트에 인증 정보를 저장
                UserDetails userDetails = userDetailService.loadUserByUsername(req.getUserEmail());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

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
    public BindingResult editValid(UserDto dto, BindingResult bindingResult, String userEmail){
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

    @Transactional
    public void edit(UserDto dto, String userEmail){
        User loginUser = userRepository.findByUserEmail(userEmail).get();

        if(dto.getNewUserPassword().equals("")){
            loginUser.edit(loginUser.getUserPassword(), dto.getUserName());
        }else {
            loginUser.edit(encoder.encode(dto.getNewUserPassword()), dto.getUserName());
        }
    }

    @Transactional
    public Boolean delete(String userEmail, String nowUserPassword){
        User loginUser = userRepository.findByUserEmail(userEmail).get();
        if (encoder.matches(nowUserPassword, loginUser.getUserPassword())) {
            userRepository.delete(loginUser);
            return true;
        }else {
            return false;
        }
    }

    public Page<User> findAllByUserName(String keyword, PageRequest pageRequest){
        return userRepository.findAllByUserNameContains(keyword, pageRequest);
    }
}
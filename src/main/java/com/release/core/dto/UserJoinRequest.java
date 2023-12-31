package com.release.core.dto;

import com.release.core.domain.User;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class UserJoinRequest {
    private String userEmail;

    private String userName;
    private String userPassword;
    private String userPasswordCheck;


    public User toEntity(String encodedPassword){
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPassword(userPassword)
                .userRegisterDate(LocalDateTime.now().toString())
                .userRole(User.UserRole.USER)
                .build();
    }
}


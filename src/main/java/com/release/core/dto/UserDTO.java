package com.release.core.dto;

import com.release.core.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String userEmail;
    private String userName;
    private String nowUserPassword;
    private String newUserPassword;
    private String newUserPasswordCheck;

    public static UserDTO of(User user){
        return UserDTO.builder()
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .build();
    }
}

package com.release.core.dto;

import com.release.core.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String userEmail;
    private String userName;
    private String nowUserPassword;
    private String newUserPassword;
    private String newUserPasswordCheck;

    public static UserDto of(User user){
        return UserDto.builder()
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .build();
    }
}

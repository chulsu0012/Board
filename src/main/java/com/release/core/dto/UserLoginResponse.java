package com.release.core.dto;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String message;
    private Long userId;

    public UserLoginResponse(String message) {
        this.message = message;
    }
    public UserLoginResponse(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

}

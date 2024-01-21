package com.release.core.dto;

import lombok.Data;

@Data
public class UserJoinResponse {
    private String message;
    private String userName;
    private String userEmail;

    public UserJoinResponse(String message) {
        this.message = message;
    }

    public UserJoinResponse(String message, String userName, String userEmail) {
        this.message = message;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}

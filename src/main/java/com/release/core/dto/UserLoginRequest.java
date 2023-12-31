package com.release.core.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String userEmail;
    private String userPassword;
}

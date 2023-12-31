package com.release.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserLoginRequest {
    private String userEmail;
    private String userPassword;
}

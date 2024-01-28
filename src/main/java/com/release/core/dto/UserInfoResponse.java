package com.release.core.dto;

import lombok.Data;

@Data
public class UserInfoResponse {
    private long sauserId;
    private String userName;
    private String userEmail;
    private String userRegisterDate;
    private String userRole;

    public UserInfoResponse(long sauserId, String userName, String userEmail, String userRegisterDate, String userRole) {
        this.sauserId = sauserId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRegisterDate = userRegisterDate;
        this.userRole = userRole;
    }
}

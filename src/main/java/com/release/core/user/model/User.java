package com.release.core.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "UserTable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private int userId;

    private int userIsAdmin;
    private String userName;
    private String userEmail;
    private String userPassword;

    @Column(name = "userRegisterDate")
    private LocalDateTime userRegisterDate;
//    @Builder
//    public User(String userName, String userPassword, String userEmail){
//        this.userName = userName;
//        this.userEmail = userEmail;
//        this.userPassword = userPassword;
//    }
//    protected User(){}
//
//    public void setUserName(String john) {
//    }
//
//    public void setUserPassword(String password) {
//    }
//
//    public void setUserEmail(String s) {
//    }
//
//    public void setUserIsAdmin(int i) {
//    }
//
//    public void getUserRegisterDate(LocalDateTime now) {
//    }

    // getters and setters
}

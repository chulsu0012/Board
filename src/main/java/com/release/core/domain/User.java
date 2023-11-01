package com.release.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "UserTable")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 20, unique = true)
    private String userName;

    @Column(nullable = false, length = 20)
    private String userEmail;

    @Column(nullable = false, length = 20)
    private String userPassword;

    private String userRegisterDate;


    @Column(name = "UserIsAdmin")
    private Long UserIsAdmin;

    public Long getId() {
        return userId;
    }
    public void setId(Long id) {
        this.userId = id;
    }
    public String getName() {
        return userName;
    }
    public void setName(String name) {
        this.userName = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(String userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    public Long getUserIsAdmin() {
        return UserIsAdmin;
    }

    public void setUserIsAdmin(Long userIsAdmin) {
        UserIsAdmin = userIsAdmin;
    }

}
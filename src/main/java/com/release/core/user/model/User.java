package com.release.core.user.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private int userId;

    private int userIsAdmin;
    private String userName;
    private String userEmail;
    private String userPassword;

//    @Column(name = "userRegisterDate")
    private LocalDateTime userRegisterDate;

    // getters and setters
}

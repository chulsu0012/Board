package com.release.core.user.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private int userIsAdmin;
    private String userName;
    private String userEmail;
    private String userPassword;

    @Column(name = "user_register_date")
    private LocalDateTime userRegisterDate;

    // getters and setters
}

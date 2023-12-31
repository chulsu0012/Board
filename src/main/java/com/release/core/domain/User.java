package com.release.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "UserTable")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", updatable = false)
    private Long userId;
    @Column(name = "userEmail", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userPassword")
    private String userPassword;

    @Column(name = "userRegisterDate")
    private String userRegisterDate;

    @Column(name = "userIsAdmin")
    private Integer userIsAdmin;

    public enum UserRole {
        USER, ADMIN;
    }

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public void edit(String newUserPassword, String newUserName) {
        this.userPassword = newUserPassword;
        this.userName = newUserName;
    }
}


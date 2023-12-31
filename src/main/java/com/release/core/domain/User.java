package com.release.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "USERTABLE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID", updatable = false)
    private Long userId;
    @Column(name = "USEREMAIL", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USERPASSWORD")
    private String userPassword;

    @Column(name = "USERREGISTERDATE")
    private String userRegisterDate;

    //@Column(name = "USERISADMIN")
    //private Integer userIsAdmin;

    public enum UserRole {
        USER, ADMIN;
    }

    @Column(name = "USERROLE")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public void edit(String newUserPassword, String newUserName) {
        this.userPassword = newUserPassword;
        this.userName = newUserName;
    }
}


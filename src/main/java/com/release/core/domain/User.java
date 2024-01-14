package com.release.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Table(name = "USERTABLE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /*
    public String getUserName() {
        return userName;
    }
    // UserDetails 인터페이스의 메서드 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 설정 (필요에 따라 수정)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 설정 (필요에 따라 수정)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부 설정 (필요에 따라 수정)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성 여부 설정 (필요에 따라 수정)
    }

     */
}


package com.release.core.service;

import com.release.core.domain.User;
import com.release.core.dto.AddUserRequestDTO;
import com.release.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequestDTO dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // (1) 패스워드 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
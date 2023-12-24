package com.release.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequestDTO {
    private String email;
    private String password;
}

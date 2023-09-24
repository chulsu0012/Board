package com.release.core.controller;

import com.release.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // RESTful API 엔드포인트 정의 (GET, POST, PUT, DELETE)
    // ...

}

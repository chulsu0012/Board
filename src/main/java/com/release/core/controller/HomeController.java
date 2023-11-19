package com.release.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/ping")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

}

package com.release.core.controller;

//import com.mysql.cj.x.protobuf.MysqlxNotice;
import com.release.core.domain.User;
import com.release.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController{
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public Long joinUser(@RequestBody User user) {
        // 클라이언트로부터의 요청 데이터를 User 객체로 받아옵니다.
        // 예를 들어, JSON 형식의 요청 데이터를 User 객체로 변환합니다.

        // 중복 회원 검증
        userService.validateDuplicateUser(user);

        // UserService의 join 메서드를 호출하여 회원가입을 수행합니다.
        Long userId = userService.join(user);

        // 회원가입이 성공하면 생성된 회원의 ID를 반환합니다.
        return userId;
    }
/*
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        // UserService를 사용하여 사용자 로그인을 처리
        // 성공하면 토큰 또는 세션을 발급하고 성공 메시지를 반환
        // 실패하면 에러 메시지를 반환
    }


    @PostMapping("/logout")
    public void logoutUser() {
        // 로그아웃 로직을 구현
    }


    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        // UserService를 사용하여 사용자 정보 수정 처리
        // 성공하면 업데이트된 사용자 정보를 반환
    }
    @DeleteMapping("/users/{userId}")
public void deleteUser(@PathVariable Long userId) {
    // UserService를 사용하여 사용자 삭제 처리
}
    */
    /*
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "board!!");
        return "hello";
    }
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    //json

    static class Hello{
        private  String name;

        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
        // getter setter 자바 빈 규약
        // 프로퍼티 접근 방식
    }
*/
}

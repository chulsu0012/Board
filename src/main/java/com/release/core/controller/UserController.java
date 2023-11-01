package com.release.core.controller;

//import com.mysql.cj.x.protobuf.MysqlxNotice;
import com.release.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController{
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }




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

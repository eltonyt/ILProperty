package com.heyprojecthub.ilproperty.controller;

import com.heyprojecthub.ilproperty.domain.ResponseResult;
import com.heyprojecthub.ilproperty.domain.User;
import com.heyprojecthub.ilproperty.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        return loginService.login(user);
    }

    @RequestMapping("/user/logout")
    public ResponseResult logOut() {
        return loginService.logOut();
    }
}

package com.csonezp.daodemo.starter.controller;

import com.csonezp.daodemo.application.UserApplication;
import com.csonezp.daodemo.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserApplication userApplication;

    @GetMapping("/findUser")
    public User findUser(Long id) {
        return userApplication.findById(id);
    }
}

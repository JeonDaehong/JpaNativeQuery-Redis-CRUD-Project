package com.example.crudproject.controller;

import com.example.crudproject.common.ResponseCode;
import com.example.crudproject.domain.User;
import com.example.crudproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final UserService userService;

    @PostMapping("/user/join")
    public String joinUser(@RequestParam(value = "loginId") String loginId,
                           @RequestParam(value = "userName") String userName,
                           @RequestParam(value = "password") String password) {

        User user = new User();
        user.setLoginId(loginId);
        user.setUserName(userName);
        user.setPassword(password);

        return userService.joinUser(user);
    }

}

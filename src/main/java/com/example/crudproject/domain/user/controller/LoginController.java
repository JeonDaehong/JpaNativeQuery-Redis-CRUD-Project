package com.example.crudproject.domain.user.controller;

import com.example.crudproject.common.code.ResponseCode;
import com.example.crudproject.domain.user.entity.dto.UserLoginDto;
import com.example.crudproject.domain.user.entity.User;
import com.example.crudproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    @PostMapping("/user/login")
    public String login(@Valid UserLoginDto userDto,
                        HttpServletRequest request) {


        User user = userService.login(userDto);
        if ( user == null ) return ResponseCode.LOGIN_ERROR_CODE;

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);
        session.setMaxInactiveInterval(60 * 30);

        return ResponseCode.SUCCESS_CODE;
    }


    @PostMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}

package com.example.crudproject.controller;

import com.example.crudproject.common.ResponseCode;
import com.example.crudproject.domain.User;
import com.example.crudproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    // Validation Check
    public boolean validationCheck(String loginId, String password) {
        if (loginId.equals("") || password.equals("")) return false;
        return true;
    }

    /**
     *  로그인
     */
    @PostMapping("/user/login")
    public String login(@RequestParam(value = "loginId") String loginId,
                        @RequestParam(value = "password") String password,
                        HttpServletRequest request) {

        // Validation Check
        if (!validationCheck(loginId, password)) return ResponseCode.LOGIN_ERROR_CODE;

        User user = userService.login(loginId, password);
        if ( user == null ) return ResponseCode.LOGIN_ERROR_CODE;

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);
        session.setMaxInactiveInterval(60 * 30);

        return ResponseCode.SUCCESS_CODE;
    }


    /**
     *  로그아웃
     */
    @PostMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}

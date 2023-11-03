package com.example.crudproject.controller;

import com.example.crudproject.common.ResponseCode;
import com.example.crudproject.domain.User;
import com.example.crudproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /*
     * Validation Check Overload Method
     */
    public boolean validationCheck(String loginId, String userName, String password) {
        if (loginId.equals("") || userName.equals("") || password.equals("")) return false;
        return true;
    }
    public boolean validationCheck(String loginId, String userName) {
        if (loginId.equals("") || userName.equals("")) return false;
        return true;
    }
    public boolean validationCheck(String loginId) {
        if (loginId.equals("")) return false;
        return true;
    }


    /**
     *  회원가입
     */
    @PostMapping("/user/join")
    public String joinUser(@RequestParam(value = "loginId") String loginId,
                           @RequestParam(value = "userName") String userName,
                           @RequestParam(value = "password") String password) {

        if ( ! validationCheck(loginId, userName, password) ) return ResponseCode.ERROR_CODE;

        User user = new User();
        user.setLoginId(loginId);
        user.setUserName(userName);
        user.setPassword(password);

        return userService.joinUser(user);
    }

    /**
     *  내 정보 수정하기
     */
    @PostMapping("/user/update")
    public String updateUser(@RequestParam(value = "loginId") String loginId,
                             @RequestParam(value = "userName") String userName,
                             HttpServletRequest request, HttpSession session) {

        // Session 끊킬 시 redirect
        User sessionUser = (User) session.getAttribute("loginUser");
        boolean loginSession = sessionUser != null;
        if ( !loginSession ) return "redirect:/loginPage";

        if ( ! validationCheck(loginId, userName) ) return ResponseCode.ERROR_CODE;

        User user = new User();
        user.setLoginId(loginId);
        user.setUserName(userName);
        String resultCode = userService.updateUser(user);

        if ( resultCode.equals(ResponseCode.SUCCESS_CODE)) {
            sessionUser = (User) session.getAttribute("loginUser");
            sessionUser.setUserName(userName);
            session.setAttribute("loginUser", sessionUser);
        }
        return resultCode;
    }

    /**
     *  회원 탈퇴
     */
    @PostMapping("/user/delete")
    public String updateUser(@RequestParam(value = "loginId") String loginId, HttpSession session) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";

        if ( ! validationCheck(loginId) ) return ResponseCode.ERROR_CODE;

        String resultCode = userService.deleteUser(loginId);

        if (!resultCode.equals(ResponseCode.SUCCESS_CODE)) return ResponseCode.ERROR_CODE;

        session.invalidate();
        return resultCode;

    }

}
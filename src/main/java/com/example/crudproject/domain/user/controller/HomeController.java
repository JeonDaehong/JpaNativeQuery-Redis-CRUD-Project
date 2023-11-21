package com.example.crudproject.domain.user.controller;

import com.example.crudproject.domain.user.entity.User;
import com.example.crudproject.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final BoardService boardService;

    @GetMapping ("/")
    public String getMainPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/main";
    }


    @GetMapping("/loginPage")
    public String getLoginPage() {
        return "/login";
    }


    @GetMapping("/joinPage")
    public String getJoinPage() {
        return "/join";
    }


    @GetMapping("/userInfoPage")
    public String getUserInfoPage(HttpSession session, Model model) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";

        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/userInfo";
    }
}

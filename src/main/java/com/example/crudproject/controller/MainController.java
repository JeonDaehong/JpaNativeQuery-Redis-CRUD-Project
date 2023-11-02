package com.example.crudproject.controller;

import com.example.crudproject.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @GetMapping ("/")
    public String main(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/main";
    }

    @GetMapping ("/loginPage")
    public String loginPage() {
        return "/login";
    }

    @GetMapping ("/joinPage")
    public String joinPage() {
        return "/join";
    }

    @GetMapping("/userInfoPage")
    public String userInfo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/userInfo";
    }

}

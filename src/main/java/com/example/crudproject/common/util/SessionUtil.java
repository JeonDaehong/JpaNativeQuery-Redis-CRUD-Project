package com.example.crudproject.common.util;

import com.example.crudproject.domain.user.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.net.URI;

public class SessionUtil {

    public static boolean checkSession(HttpSession session) {
        User sessionUser = (User) session.getAttribute("loginUser");
        return sessionUser == null;
    }

    public static ResponseEntity<?> redirect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/loginPage"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}

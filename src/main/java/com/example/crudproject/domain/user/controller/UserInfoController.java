package com.example.crudproject.domain.user.controller;

import com.example.crudproject.common.code.ResponseCode;
import com.example.crudproject.common.util.SessionUtil;
import com.example.crudproject.domain.board.service.ScoreService;
import com.example.crudproject.domain.user.entity.dto.UserDeleteDto;
import com.example.crudproject.domain.user.entity.dto.UserJoinDto;
import com.example.crudproject.domain.user.entity.dto.UserUpdateDto;
import com.example.crudproject.domain.board.service.BoardService;
import com.example.crudproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserInfoController {

    private final UserService userService;


    @PostMapping("/user/join")
    public ResponseEntity<?> joinUser(@Valid @RequestBody UserJoinDto userDto) {
        return ResponseEntity.ok(userService.joinUser(userDto));
    }


    @PostMapping("/user/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDto userDto,
                                        HttpSession session) {
        if (SessionUtil.checkSession(session)) return SessionUtil.redirect(); // Session 끊킬 시 redirect
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @PostMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDeleteDto userDto,
                                        HttpSession session) {

        if (SessionUtil.checkSession(session)) return SessionUtil.redirect();

        String resultCode = userService.deleteUser(userDto);

        // 정상적으로 삭제 시 Session 끊기
        if (resultCode.equals(ResponseCode.SUCCESS_CODE)) session.invalidate();

        return ResponseEntity.ok(resultCode);

    }

}

package com.example.crudproject.domain.board.controller;

import com.example.crudproject.common.util.SessionUtil;
import com.example.crudproject.domain.board.entity.dto.ScoreDto;
import com.example.crudproject.domain.board.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/board/score/register")
    public ResponseEntity<?> registerScore(@Valid ScoreDto scoreDto,
                                           HttpSession session) {

        if (SessionUtil.checkSession(session)) return SessionUtil.redirect(); // Session 끊킬 시 redirect
        return ResponseEntity.ok(scoreService.registerScore(scoreDto));
    }

    @PostMapping("/board/score/update")
    public ResponseEntity<?> updateScore(@Valid ScoreDto scoreDto,
                                         HttpSession session) {

        if (SessionUtil.checkSession(session)) return SessionUtil.redirect(); // Session 끊킬 시 redirect
        return ResponseEntity.ok(scoreService.updateScore(scoreDto));
    }

}

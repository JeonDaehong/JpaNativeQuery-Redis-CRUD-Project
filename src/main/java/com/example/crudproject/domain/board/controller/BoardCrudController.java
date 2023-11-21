package com.example.crudproject.domain.board.controller;

import com.example.crudproject.common.util.SessionUtil;
import com.example.crudproject.domain.board.entity.dto.BoardWriteDto;
import com.example.crudproject.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardCrudController {

    private final BoardService boardService;

    @PostMapping("/board/write")
    public ResponseEntity<?> writeBoard(@Valid BoardWriteDto boardDto,
                                        HttpSession session) {
        // Session 끊킬 시 redirect
        if (SessionUtil.checkSession(session)) return SessionUtil.redirect(); // Session 끊킬 시 redirect
        return ResponseEntity.ok(boardService.writeBoard(boardDto)); // insert
    }


    @PostMapping("/board/update")
    public ResponseEntity<?> updateBoard(@Valid BoardWriteDto boardDto,
                                         HttpSession session) {
        if (SessionUtil.checkSession(session)) return SessionUtil.redirect(); // Session 끊킬 시 redirect
        return ResponseEntity.ok(boardService.updateBoard(boardDto)); // update
    }


    @PostMapping("/board/delete")
    public ResponseEntity<?> deleteBoard(@RequestParam(value = "boardId") Long boardId,
                              HttpSession session) {
        if (SessionUtil.checkSession(session)) return SessionUtil.redirect(); // Session 끊킬 시 redirect
        return ResponseEntity.ok(boardService.deleteBoard(boardId)); // delete
    }
}

package com.example.crudproject.controller;

import com.example.crudproject.domain.Board;
import com.example.crudproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/insert/board")
    public void insertBoard(@Valid @RequestBody Board board, BindingResult bindingResult) {

        if ( bindingResult.hasErrors() ) {
            throw new NullPointerException("A null pointer error occurred during validation check.");
        } else {
            board.setContentView(0);
            board.setCreateDateTime(LocalDateTime.now());
            board.setUpdateDateTime(LocalDateTime.now());

            boardService.insertPost(board);
        }
    }
}

package com.example.crudproject.domain.board.controller;

import com.example.crudproject.domain.board.entity.vo.BoardVo;
import com.example.crudproject.domain.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BoardControllerTest {

    @Autowired
    private BoardService boardService;

    @Test
    void completableFutureTest() {
        BoardVo boardVo = boardService.getBoardInfo(80L);
        System.out.println(boardVo.toString());
    }

}

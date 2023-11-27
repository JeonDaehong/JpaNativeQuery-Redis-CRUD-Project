package com.example.crudproject.domain.board.controller;

import com.example.crudproject.domain.board.entity.dto.ScoreDto;
import com.example.crudproject.domain.board.entity.vo.BoardVo;
import com.example.crudproject.domain.board.service.BoardService;
import com.example.crudproject.domain.board.service.ScoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ScoreControllerTest {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("스코어 추가 로직 테스트")
    void registerScoreTest() {

        // given
        Long testBoardId = 3L;
        Long testUserId = 5L;
        int testScore = 5;

        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setBoardId(testBoardId);
        scoreDto.setUserId(testUserId);
        scoreDto.setScore(testScore);

        // when
        scoreService.registerScore(scoreDto);

        // then
        BoardVo boardVo = boardService.getBoardInfo(testBoardId);
        assertEquals(boardVo.getAverageScore(), testScore);

    }

    @Test
    @DisplayName("스코어 확인 테스트")
    void getBoardScoreTest() {

        // then
        BoardVo boardVo = new BoardVo();
        boardVo = boardService.getBoardInfo(2L);
        System.out.println(boardVo.toString());

    }

}

package com.example.crudproject.domain.board.service;

import com.example.crudproject.common.code.ResponseCode;
import com.example.crudproject.domain.board.entity.dto.ScoreDto;
import com.example.crudproject.domain.board.entity.vo.ScoreVo;
import com.example.crudproject.domain.board.repository.BoardRepository;
import com.example.crudproject.domain.board.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final BoardRepository boardRepository;

    /**
     * 평점 추가
     */
    @Transactional
    @Override
    public String registerScore(ScoreDto scoreDto) {

        try {
            ScoreVo scoreVo = ScoreVo.fromScoreDto(scoreDto);
            scoreVo.setCreateDateTime(LocalDateTime.now());
            scoreVo.setUpdateDateTime(LocalDateTime.now());
            scoreRepository.registerScore(scoreVo.getUserId(),
                                          scoreVo.getBoardId(),
                                          scoreVo.getScore(),
                                          scoreVo.getCreateDateTime(),
                                          scoreVo.getUpdateDateTime());
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    }

    /**
     * 평점 수정
     */
    @Transactional
    @Override
    public String updateScore(ScoreDto scoreDto) {
        try {
            ScoreVo scoreVo = ScoreVo.fromScoreDto(scoreDto);
            scoreVo.setUpdateDateTime(LocalDateTime.now());
            scoreRepository.updateScore(scoreVo.getUserId(),
                    scoreVo.getBoardId(),
                    scoreVo.getScore(),
                    scoreVo.getUpdateDateTime());
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    }

    /**
     * 평점 계산
     */
    @Async
    @Transactional
    @Override
    public void calculateRating() {
        List<Long> boardIdList = boardRepository.getAllBoardId();
        for (Long boardId : boardIdList) {
            double avgScore = scoreRepository.getBoardAverageScore(boardId);
            boardRepository.updateAverageScore(boardId, avgScore, LocalDateTime.now());
        }
    }

}

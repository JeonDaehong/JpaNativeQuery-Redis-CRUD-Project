package com.example.crudproject.common.batch;

import com.example.crudproject.domain.board.service.BoardService;
import com.example.crudproject.domain.board.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisBatch {

    private final BoardService boardService;
    private final ScoreService scoreService;

    @Scheduled(fixedDelay = 1000 * 60) // 1분에 한 번 실행
    public void transferBoardViewRedisToDB() {
        boardService.transferBoardViewRedisToDB();
    }

    @Scheduled(fixedDelay = 1000 * 60) // 1분에 한 번 실행
    public void insertAverageScoreDB() {
        scoreService.calculateRating();
    }
}
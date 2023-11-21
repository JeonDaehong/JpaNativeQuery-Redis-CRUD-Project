package com.example.crudproject.common.batch;

import com.example.crudproject.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisBatch {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final BoardService boardService;

    @Scheduled(fixedDelay = 1000 * 60 * 60) // 1시간(60분)에 한 번 실행
    public void transferBoardViewRedisToDB() {
        boardService.transferBoardViewRedisToDB();
    }
}
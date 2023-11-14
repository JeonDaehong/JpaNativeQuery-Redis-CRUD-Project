package com.example.crudproject.batch;

import com.example.crudproject.common.RedisStringCode;
import com.example.crudproject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Component
@RequiredArgsConstructor
public class RedisBatch {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final BoardRepository boardRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 60) // 1시간(60분)에 한 번 실행
    public void getAllBoardKeysPeriodically() {

        List<String> boardKeys = new ArrayList<>();
        ScanOptions scanOptions = ScanOptions.scanOptions().match(RedisStringCode.BOARD_KEY_CODE + "*").build();

        Cursor<byte[]> cursor = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().scan(scanOptions);
        while (cursor.hasNext()) {
            byte[] keyBytes = cursor.next();
            String key = new String(keyBytes, StandardCharsets.UTF_8);
            boardKeys.add(key);
        }
        cursor.close();

        // 조회된 키에 대한 추가 작업 수행
        for (String key : boardKeys) {
            long boardId = Long.parseLong(key.replace(RedisStringCode.BOARD_KEY_CODE, ""));
            Integer addView = redisTemplate.opsForValue().get(key);
            if ( addView != null ) {
                boardRepository.addBoardViewRedis(boardId, addView, LocalDateTime.now());

                // 정상적으로 처리되었으면 Redis 에서 해당 key 삭제
                redisTemplate.delete(key);
            }
        }
    }
}
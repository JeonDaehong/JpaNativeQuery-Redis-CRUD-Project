package com.example.crudproject;

import com.example.crudproject.common.RedisStringCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplateBoardView;

    @Test
    void testStrings() {
        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        // then
        String value = valueOperations.get(key);
        assertThat(value).isEqualTo("hello");
    }


    @Test
    void testSet() {
        // given
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        String key = "setKey";

        // when
        setOperations.add(key, "h", "e", "l", "l", "o");

        // then
        Set<String> members = setOperations.members(key);
        Long size = setOperations.size(key);

        assertThat(members).containsOnly("h", "e", "l", "o");
        assertThat(size).isEqualTo(4);
    }

    @Test
    void testHash() {

        // given
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String key = "hashKey";

        // when
        hashOperations.put(key, "hello", "world");

        // then
        Object value = hashOperations.get(key, "hello");
        assertThat(value).isEqualTo("world");

        Map<Object, Object> entries = hashOperations.entries(key);
        assertThat(entries.keySet()).containsExactly("hello");
        assertThat(entries.values()).containsExactly("world");

        Long size = hashOperations.size(key);
        assertThat(size).isEqualTo(entries.size());
    }

    @Test
    void getAllKeys() {

        // 패턴을 "*"로 설정하여 모든 키를 조회합니다.
        ScanOptions scanOptions = ScanOptions.scanOptions().match(RedisStringCode.BOARD_KEY_CODE + "*").build();

        // Redis 서버에서 모든 키를 스캔합니다.
        Cursor<byte[]> cursor = Objects.requireNonNull(redisTemplateBoardView.getConnectionFactory()).getConnection().scan(scanOptions);
        while (cursor.hasNext()) {
            byte[] keyBytes = cursor.next();
            String key = new String(keyBytes, StandardCharsets.UTF_8);
            Integer value = redisTemplateBoardView.opsForValue().get(key);
            System.out.println("Key: " + key + ", Value: " + value);
        }
        cursor.close();
    }

}

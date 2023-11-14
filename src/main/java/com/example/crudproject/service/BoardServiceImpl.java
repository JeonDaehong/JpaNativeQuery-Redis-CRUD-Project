package com.example.crudproject.service;

import com.example.crudproject.common.RedisStringCode;
import com.example.crudproject.common.ResponseCode;
import com.example.crudproject.domain.Board;
import com.example.crudproject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final RedisTemplate<String, Integer> redisViewTemplate;

    /**
     * 게시글 작성
     */
    @Override
    public String writeBoard(String title, String content, Long userId) {

        try {

            boardRepository.writeBoard(
                    title,
                    content,
                    0,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    userId);

            return ResponseCode.SUCCESS_CODE;

        } catch ( Exception e ) {
            return ResponseCode.ERROR_CODE;
        }
    }

    /**
     * 게시글 갯수 반환 ( 페이징 처리 )
     */
    @Override
    public int getBoardCount() {
        return boardRepository.getBoardCount();
    }

    /**
     * 게시글 리스트 반환
     */
    @Override
    public List<Board> getBoardList(int startIdx, int count) {
        return boardRepository.getBoardList(startIdx, count);
    };

    /**
     * 게시글 상세 정보 반환
     */
    @Override
    public Board getBoardInfo(Long boardId) {
        return boardRepository.getBoardInfo(boardId);
    }

    /**
     * 게시글 조회수 Redis에 넣고 반환하기
     */
    @Override
    public int getBoardViewRedisIncrement(Long boardId) {
        String redisKey = RedisStringCode.BOARD_KEY_CODE + boardId;

        ValueOperations<String, Integer> ops = redisViewTemplate.opsForValue();
        Integer redisView = ops.get(redisKey);
        if ( redisView != null ) {
            long view = Objects.requireNonNullElse(ops.increment(redisKey), 1L);
            return (int)view;
        } else {
            ops.set(redisKey, 1);
            return 1;
        }
    }

    /**
     * Redis에 있는 조회수 그냥 반환하기
     */
    @Override
    public int getBoardViewRedis(Long boardId) {
        String redisKey = RedisStringCode.BOARD_KEY_CODE + boardId;

        ValueOperations<String, Integer> ops = redisViewTemplate.opsForValue();
        Integer redisView = ops.get(redisKey);
        return Objects.requireNonNullElse(redisView, 0);
    }

    /**
     * 게시글 수정
     */
    @Override
    public String updateBoard(String title, String content, Long boardId){
        try {
            boardRepository.updateBoard(boardId, title, content, LocalDateTime.now());
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR_CODE;
        }
    };

    /**
     * 게시글 삭제
     */
    @Override
    public String deleteBoard(Long boardId){
        try {
            boardRepository.deleteBoard(boardId);
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR_CODE;
        }
    };

    /**
     * 해당 유저의 게시글 모두 삭제
     */
    @Override
    public void deleteBoardAllByUser(Long userId) {
        boardRepository.deleteBoardAllByUser(userId);
    }
}

package com.example.crudproject.domain.board.service;

import com.example.crudproject.common.code.RedisStringCode;
import com.example.crudproject.common.code.ResponseCode;
import com.example.crudproject.domain.board.entity.dto.BoardWriteDto;
import com.example.crudproject.domain.board.entity.Board;
import com.example.crudproject.domain.board.entity.vo.BoardVo;
import com.example.crudproject.domain.board.repository.BoardRepository;
import com.example.crudproject.domain.board.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ScoreRepository scoreRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 게시판 글 작성
     */
    @Transactional
    @Override
    public String writeBoard(BoardWriteDto boardDto) {

        try {
            BoardVo boardVo = BoardVo.fromBoardWriteDto(boardDto);
            boardVo.setBoardView(0);
            boardVo.setCreateDateTime(LocalDateTime.now());
            boardVo.setUpdateDateTime(LocalDateTime.now());
            boardRepository.writeBoard(
                    boardVo.getTitle(),
                    boardVo.getContent(),
                    boardVo.getBoardView(),
                    boardVo.getCreateDateTime(),
                    boardVo.getUpdateDateTime(),
                    boardVo.getUserId());
            return ResponseCode.SUCCESS_CODE;
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    }

    /**
     * 페이징 처리를 위한 전체 게시글 갯수 조회
     */
    @Override
    public int getBoardCount() {
        return boardRepository.getBoardCount();
    }


    /**
     * 게시글 목록 가져오기
     */
    @Override
    public List<BoardVo> getBoardList(int startIdx, int count) {

        List<BoardVo> boardVoList = new ArrayList<>();
        for ( Board board : boardRepository.getBoardList(startIdx, count) ) {
            BoardVo boardVo = BoardVo.fromBoardEntity(board);
            boardVo.setBoardView(board.getBoardView() + getBoardViewRedis(board.getBoardId())); // 조회수
            boardVo.setAverageScore(scoreRepository.getBoardAverageScore(board.getBoardId())); // 평점
            boardVoList.add(boardVo);
        }

        return boardVoList;

    };

    /**
     * 게시글 정보 가져오기
     */
    @Override
    public BoardVo getBoardInfo(Long boardId) {
        Board board = boardRepository.getBoardInfo(boardId);
        BoardVo boardVo = BoardVo.fromBoardEntity(board);
        boardVo.setBoardView(board.getBoardView() + getBoardViewRedis(board.getBoardId())); // 조회수
        boardVo.setAverageScore(scoreRepository.getBoardAverageScore(board.getBoardId())); // 평점

        return boardVo;
    }


    /**
     * Redis 에 조회수 추가 후 반환
     */
    @Transactional
    @Override
    public int getBoardViewRedisIncrement(Long boardId) {
        String redisKey = RedisStringCode.BOARD_KEY_CODE;
        String redisHashKey = String.valueOf(boardId);

        HashOperations<String, Object, Integer> hashOperations = redisTemplate.opsForHash();
        Integer redisView = hashOperations.get(redisKey, redisHashKey);
        if ( redisView != null ) {
            return Math.toIntExact(hashOperations.increment(redisKey, redisHashKey, 1));
        } else {
            hashOperations.put(redisKey, redisHashKey, 1);
            return 1;
        }
    }


    /**
     * Redis 에 담긴 조회수 그대로 반환
     */
    @Override
    public int getBoardViewRedis(Long boardId) {
        String redisKey = RedisStringCode.BOARD_KEY_CODE;
        String redisHashKey = String.valueOf(boardId);

        HashOperations<String, Object, Integer> hashOperations = redisTemplate.opsForHash();
        Integer redisView = hashOperations.get(redisKey, redisHashKey);
        return Objects.requireNonNullElse(redisView, 0);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    @Override
    public String updateBoard(BoardWriteDto boardDto){
        try {
            BoardVo boardVo = BoardVo.fromBoardWriteDto(boardDto);
            boardVo.setUpdateDateTime(LocalDateTime.now());

            boardRepository.updateBoard(boardVo.getBoardId(), boardVo.getTitle(), boardVo.getContent(), boardVo.getUpdateDateTime());
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    };

    /**
     * 게시글 삭제
     */
    @Transactional
    @Override
    public String deleteBoard(Long boardId){
        try {
            scoreRepository.deleteScoreByBoard(boardId); // 해당 게시글과 관련된 평점 삭제 (외래키 연결)
            boardRepository.deleteBoard(boardId); // 해당 게시글 삭제
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    };


    /**
     * Redis 의 조회수를 전부 DB로 이동
     */
    @Transactional
    @Override
    public void transferBoardViewRedisToDB() {

        Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(RedisStringCode.BOARD_KEY_CODE);
        for (Map.Entry<Object, Object> entry : hashEntries.entrySet()) {
            String key = (String) entry.getKey();
            Integer addView = (Integer) entry.getValue();
            if (addView != null) boardRepository.addBoardView(Long.parseLong(key), addView, LocalDateTime.now());
        }

        // Remove key
        redisTemplate.delete(RedisStringCode.BOARD_KEY_CODE);
    }

}

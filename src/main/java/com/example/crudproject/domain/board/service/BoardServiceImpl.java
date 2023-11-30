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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

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
            boardVo.setAverageScore(0D);
            boardVo.setCreateDateTime(LocalDateTime.now());
            boardVo.setUpdateDateTime(LocalDateTime.now());
            boardRepository.writeBoard(
                    boardVo.getTitle(),
                    boardVo.getContent(),
                    boardVo.getBoardView(),
                    boardVo.getAverageScore(),
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

//      병렬 프로그래밍을 한 것과 안 한 것의 성능 차이가 많이 발생
//      Board board = boardRepository.getBoardInfo(boardId);
//      BoardVo boardVo = BoardVo.fromBoardEntity(board);
//      boardVo.setBoardView(board.getBoardView() + getBoardViewRedis(boardId));
//      incrementRedisBoardView(boardId);
//      double averageScore = scoreRepository.getBoardAverageScore(boardId);
//      boardVo.setAverageScore(averageScore);
//      return boardVo;

        incrementRedisBoardView(boardId); // Async

        // Parallel Processing
        CompletableFuture<Board> boardFuture = CompletableFuture.supplyAsync(() -> boardRepository.getBoardInfo(boardId));
        CompletableFuture<Integer> redisViewFuture = CompletableFuture.supplyAsync(() -> getBoardViewRedis(boardId));

        return boardFuture.thenCombine(redisViewFuture, (board, redisView) -> {
            BoardVo boardVo = BoardVo.fromBoardEntity(board);
            boardVo.setBoardView(board.getBoardView() + redisView);

            double averageScore = scoreRepository.getBoardAverageScore(boardId);
            boardVo.setAverageScore(averageScore);

            return boardVo;
        }).join(); // 대기하고 결과 반환
    }

    /**
     * Redis 에 조회수 추가 후 반환 ( 비동기로 처리 하는 메서드 )
     */
    @Async
    @Transactional
    @Override
    public void incrementRedisBoardView(Long boardId) {
        String redisKey = RedisStringCode.BOARD_KEY_CODE;
        String redisHashKey = String.valueOf(boardId);

        HashOperations<String, Object, Integer> hashOperations = redisTemplate.opsForHash();

        hashOperations.increment(redisKey, redisHashKey, 1);

//      이렇게 할 경우 hashOperations.put(redisKey, redisHashKey, 1); 때문에 동시성 문제 발생.
//      Integer redisView = hashOperations.get(redisKey, redisHashKey);
//      if ( redisView != null ) {
//          hashOperations.increment(redisKey, redisHashKey, 1);
//      } else {
//          hashOperations.put(redisKey, redisHashKey, 1);
//      }
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
    @Async
    @Transactional
    @Override
    public void transferBoardViewRedisToDB() {

        Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(RedisStringCode.BOARD_KEY_CODE);
        for (Map.Entry<Object, Object> entry : hashEntries.entrySet()) {
            if (entry.getKey() != null) {
                System.out.println(entry.getKey());
                String key = (String) entry.getKey();
                Integer addView = (Integer) entry.getValue();
                if (addView != null) boardRepository.addBoardView(Long.parseLong(key), addView, LocalDateTime.now());
            }
        }

        // Remove key
        redisTemplate.delete(RedisStringCode.BOARD_KEY_CODE);
    }

}

package com.example.crudproject.repository;

import com.example.crudproject.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * 게시글 작성
     */
    @Modifying
    @Query ( value = "INSERT INTO TB_BOARD (TITLE, CONTENT, BOARD_VIEW, CRTE_DTTM, UPDT_DTTM, USER_ID)" +
                     "VALUES (:title, :content, :boardView, :createDateTime, :updateDatetime, :userId)",
            nativeQuery = true )
    void writeBoard(@Param(value = "title") String title,
                    @Param(value = "content") String content,
                    @Param(value = "boardView") int boardView,
                    @Param(value = "createDateTime") LocalDateTime createDateTime,
                    @Param(value = "updateDatetime") LocalDateTime updateDatetime,
                    @Param(value = "userId") Long userId);

    /**
     * 게시글 총 갯수 반환 ( 페이징 처리를 위함 )
     */
    @Query ( value = "SELECT COUNT(*) FROM TB_BOARD",
             nativeQuery = true)
    int getBoardCount();

    /**
     * 게시글 리스트 반환
     */
    @Query (value = "SELECT * FROM TB_BOARD ORDER BY CRTE_DTTM DESC LIMIT :startIdx, :count",
            nativeQuery = true)
    List<Board> getBoardList(@Param("startIdx") int startIdx,
                             @Param("count") int count);

    /**
     * 게시글 상세 정보 반환
     */
    @Query ( value = "SELECT * FROM TB_BOARD WHERE BOARD_ID = :boardId", nativeQuery = true)
    Board getBoardInfo(@Param("boardId") Long boardId);

    /**
     * Redis 조회수 추가
     */
    @Transactional
    @Modifying
    @Query (value = "UPDATE TB_BOARD SET BOARD_VIEW = BOARD_VIEW + :addView, UPDT_DTTM = :updateDateTime WHERE BOARD_ID = :boardId", nativeQuery = true)
    void addBoardViewRedis(@Param("boardId") Long boardId,
                           @Param("addView") int addView,
                           @Param("updateDateTime") LocalDateTime updateDateTime);

    /**
     * 일반적인 조회수 추가
     */
    @Transactional
    @Modifying
    @Query (value = "UPDATE TB_BOARD SET BOARD_VIEW = BOARD_VIEW + 1 WHERE BOARD_ID = :boardId", nativeQuery = true)
    void addBoardView(@Param("boardId") Long boardId);


    /**
     * 게시글 수정
     */
    @Modifying
    @Query (value = "UPDATE TB_BOARD SET TITLE = :title, CONTENT = :content, UPDT_DTTM = :updateDateTime WHERE BOARD_ID = :boardId",
            nativeQuery = true)
    void updateBoard(@Param("boardId") Long boardId,
                     @Param("title") String title,
                     @Param("content") String content,
                     @Param("updateDateTime") LocalDateTime updateDateTime);

    /**
     * 게시글 삭제
     */
    @Modifying
    @Query (value = "DELETE FROM TB_BOARD WHERE BOARD_ID = :boardId", nativeQuery = true)
    void deleteBoard(@Param("boardId") Long boardId);


    /**
     * 해당 유저의 게시글 모두 삭제
     */
    @Transactional
    @Modifying
    @Query (value = "DELETE FROM TB_BOARD WHERE USER_ID = :userId", nativeQuery = true)
    void deleteBoardAllByUser(@Param("userId") Long userId);

}

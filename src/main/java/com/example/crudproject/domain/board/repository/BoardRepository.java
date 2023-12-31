package com.example.crudproject.domain.board.repository;

import com.example.crudproject.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query ( value = "INSERT INTO TB_BOARD (TITLE, CONTENT, BOARD_VIEW, AVG_SCORE, CRTE_DTTM, UPDT_DTTM, USER_ID)" +
                     "VALUES (:title, :content, :boardView, :averageScore, :createDateTime, :updateDatetime, :userId)",
            nativeQuery = true )
    void writeBoard(@Param(value = "title") String title,
                    @Param(value = "content") String content,
                    @Param(value = "boardView") int boardView,
                    @Param(value = "averageScore") double averageScore,
                    @Param(value = "createDateTime") LocalDateTime createDateTime,
                    @Param(value = "updateDatetime") LocalDateTime updateDatetime,
                    @Param(value = "userId") Long userId);


    @Query ( value = "SELECT COUNT(*) FROM TB_BOARD", nativeQuery = true)
    int getBoardCount();


    @Query (value = "SELECT * FROM TB_BOARD ORDER BY CRTE_DTTM DESC LIMIT :startIdx, :count", nativeQuery = true)
    List<Board> getBoardList(@Param("startIdx") int startIdx,
                             @Param("count") int count);


    @Query ( value = "SELECT * FROM TB_BOARD WHERE BOARD_ID = :boardId", nativeQuery = true)
    Board getBoardInfo(@Param("boardId") Long boardId);


    // Redis에 조회수 추가
    @Modifying
    @Query (value = "UPDATE TB_BOARD SET BOARD_VIEW = BOARD_VIEW + :addView, UPDT_DTTM = :updateDateTime WHERE BOARD_ID = :boardId", nativeQuery = true)
    void addBoardView(@Param("boardId") Long boardId,
                      @Param("addView") int addView,
                      @Param("updateDateTime") LocalDateTime updateDateTime);

    // 일반 DB로의 조회수 추가
    @Modifying
    @Query (value = "UPDATE TB_BOARD SET BOARD_VIEW = BOARD_VIEW + 1 WHERE BOARD_ID = :boardId", nativeQuery = true)
    void addBoardView(@Param("boardId") Long boardId);


    @Modifying
    @Query (value = "UPDATE TB_BOARD SET TITLE = :title, CONTENT = :content, UPDT_DTTM = :updateDateTime WHERE BOARD_ID = :boardId",
            nativeQuery = true)
    void updateBoard(@Param("boardId") Long boardId,
                     @Param("title") String title,
                     @Param("content") String content,
                     @Param("updateDateTime") LocalDateTime updateDateTime);


    @Modifying
    @Query (value = "DELETE FROM TB_BOARD WHERE BOARD_ID = :boardId", nativeQuery = true)
    void deleteBoard(@Param("boardId") Long boardId);


    @Modifying
    @Query (value = "DELETE FROM TB_BOARD WHERE USER_ID = :userId", nativeQuery = true)
    void deleteBoardAllByUser(@Param("userId") Long userId);

    @Query ( value = "SELECT BOARD_ID FROM TB_BOARD", nativeQuery = true)
    List<Long> getAllBoardId();

    @Modifying
    @Query (value = "UPDATE TB_BOARD SET AVG_SCORE = :averageScore, UPDT_DTTM = :updateDateTime WHERE BOARD_ID = :boardId", nativeQuery = true)
    void updateAverageScore(@Param("boardId") Long boardId,
                            @Param("averageScore") double averageScore,
                            @Param("updateDateTime") LocalDateTime updateDateTime);

}

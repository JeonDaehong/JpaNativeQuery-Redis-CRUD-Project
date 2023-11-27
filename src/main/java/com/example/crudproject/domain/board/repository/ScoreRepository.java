package com.example.crudproject.domain.board.repository;

import com.example.crudproject.domain.board.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Modifying
    @Query ( value = "INSERT INTO TB_BOARD_SCORE (USER_ID, BOARD_ID, SCORE, CRTE_DTTM, UPDT_DTTM)" +
                     "VALUES(:userId, :boardId, :score, :createDateTime, :updateDateTime)",
             nativeQuery = true)
    void registerScore(@Param(value = "userId") Long userId,
                       @Param(value = "boardId") Long boardId,
                       @Param(value = "score") int score,
                       @Param(value = "createDateTime") LocalDateTime createDateTime,
                       @Param(value = "updateDateTime") LocalDateTime updateDateTime);

    @Modifying
    @Query ( value = "UPDATE TB_BOARD_SCORE SET SCORE = :score, UPDT_DTTM = :updateDateTime WHERE USER_ID = :userId AND BOARD_ID = :boardId",
             nativeQuery = true)
    void updateScore(@Param(value = "userId") Long userId,
                     @Param(value = "boardId") Long boardId,
                     @Param(value = "score") int score,
                     @Param(value = "updateDateTime") LocalDateTime updateDateTime);

    @Modifying
    @Query ( value = "DELETE FROM TB_BOARD_SCORE WHERE USER_ID = :userId",
             nativeQuery = true)
    void deleteScoreByUser(@Param(value = "userId") Long userId);

    @Modifying
    @Query ( value = "DELETE FROM TB_BOARD_SCORE WHERE BOARD_ID = :boardId",
            nativeQuery = true)
    void deleteScoreByBoard(@Param(value = "boardId") Long boardId);

    @Query( value = "SELECT IFNULL(ROUND(SUM(SCORE) / COUNT(*), 2), 0) AS AVERAGE_SCORE FROM TB_BOARD_SCORE WHERE BOARD_ID = :boardId",
            nativeQuery = true )
    double getBoardAverageScore(@Param(value = "boardId") Long boardId);

}

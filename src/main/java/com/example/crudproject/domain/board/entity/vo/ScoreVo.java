package com.example.crudproject.domain.board.entity.vo;

import com.example.crudproject.domain.board.entity.dto.BoardWriteDto;
import com.example.crudproject.domain.board.entity.dto.ScoreDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ScoreVo {

    private Long boardId;
    private Long userId;
    private int score;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public static ScoreVo fromScoreDto(ScoreDto scoreDto) {
        ScoreVo scoreVo = new ScoreVo();
        scoreVo.setBoardId(scoreDto.getBoardId());
        scoreVo.setUserId(scoreDto.getUserId());
        scoreVo.setScore(scoreDto.getScore());
        return scoreVo;
    }

}

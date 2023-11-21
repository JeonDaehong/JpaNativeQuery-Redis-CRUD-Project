package com.example.crudproject.domain.board.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ScoreDto {

    @NotNull
    private Long boardId;

    @NotNull
    private Long userId;

    @NotNull
    @Min(value = 0, message = "점수는 0점에서 10점 사이입니다.")
    @Max(value = 10, message = "점수는 0점에서 10점 사이입니다.")
    private int score;

}

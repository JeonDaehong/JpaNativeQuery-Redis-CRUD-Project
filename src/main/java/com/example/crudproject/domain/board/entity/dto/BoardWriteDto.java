package com.example.crudproject.domain.board.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BoardWriteDto {

    @NotNull
    private Long boardId;

    @NotNull(message = "타이틀은 최소 한 글자 이상, 최대 40글자 이하여야합니다.")
    @Size(min = 1, max = 40)
    private String title;

    @NotNull(message = "내용은 최소 한 글자 이상이어야합니다.")
    @Size(min = 1)
    private String content;

    private int boardView;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    @NotNull(message = "게시글에 유저 ID는 반드시 들어가야합니다.")
    private Long userId;
}

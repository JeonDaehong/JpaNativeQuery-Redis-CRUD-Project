package com.example.crudproject.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BoardVo {

    private Long boardId;
    private String title;
    private String content;
    private int boardView;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private Long userId;

}

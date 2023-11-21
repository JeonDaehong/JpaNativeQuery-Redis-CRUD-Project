package com.example.crudproject.domain.board.entity.vo;

import com.example.crudproject.domain.board.entity.dto.BoardWriteDto;
import com.example.crudproject.domain.board.entity.Board;
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
    private double averageScore;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private Long userId;

    public static BoardVo fromBoardEntity(Board board) {
        BoardVo boardVo = new BoardVo();
        boardVo.setBoardId(board.getBoardId());
        boardVo.setTitle(board.getTitle());
        boardVo.setContent(board.getContent());
        boardVo.setCreateDateTime(board.getCreateDateTime());
        boardVo.setUpdateDateTime(board.getUpdateDateTime());
        boardVo.setUserId(board.getUserId());
        return boardVo;
    }

    public static BoardVo fromBoardWriteDto(BoardWriteDto board) {
        BoardVo boardVo = new BoardVo();
        boardVo.setTitle(board.getTitle());
        boardVo.setContent(board.getContent());
        boardVo.setUserId(board.getUserId());
        return boardVo;
    }

}

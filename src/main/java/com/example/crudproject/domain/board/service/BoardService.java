package com.example.crudproject.domain.board.service;

import com.example.crudproject.domain.board.entity.dto.BoardWriteDto;
import com.example.crudproject.domain.board.entity.vo.BoardVo;

import java.util.List;

public interface BoardService {

    String writeBoard(BoardWriteDto boardDto);

    int getBoardCount();

    List<BoardVo> getBoardList(int startIdx, int page);

    BoardVo getBoardInfo(Long boardId);

    void incrementRedisBoardView(Long boardId);

    int getBoardViewRedis(Long boardId);

    String updateBoard(BoardWriteDto boardDto);

    String deleteBoard(Long boardId);

    void transferBoardViewRedisToDB();

}

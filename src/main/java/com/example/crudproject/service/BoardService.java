package com.example.crudproject.service;

import com.example.crudproject.domain.Board;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardService {

    String writeBoard(Board board);

    int getBoardCount();

    List<Board> getBoardList(int startIdx, int page);

    Board getBoardInfo(Long boardId);

    String updateBoard(Board board);

    String deleteBoard(Long boardId);

}

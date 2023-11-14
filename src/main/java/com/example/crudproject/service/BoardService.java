package com.example.crudproject.service;

import com.example.crudproject.domain.Board;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardService {

    String writeBoard(String title, String content, Long userId);

    int getBoardCount();

    List<Board> getBoardList(int startIdx, int page);

    Board getBoardInfo(Long boardId);

    int getBoardViewRedisIncrement(Long boardId);

    int getBoardViewRedis(Long boardId);

    String updateBoard(String title, String content, Long boardId);

    String deleteBoard(Long boardId);

    void deleteBoardAllByUser(Long userId);

}

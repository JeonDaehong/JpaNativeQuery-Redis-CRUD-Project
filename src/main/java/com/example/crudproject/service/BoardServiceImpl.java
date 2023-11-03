package com.example.crudproject.service;

import com.example.crudproject.common.ResponseCode;
import com.example.crudproject.domain.Board;
import com.example.crudproject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 작성
     */
    @Override
    public String writeBoard(Board board) {

        try {

            board.setCreateDateTime(LocalDateTime.now());
            board.setUpdateDateTime(LocalDateTime.now());
            board.setBoardView(0);

            boardRepository.writeBoard(board.getTitle(),
                    board.getContent(),
                    board.getBoardView(),
                    board.getCreateDateTime(),
                    board.getUpdateDateTime(),
                    board.getUserId());

            return ResponseCode.SUCCESS_CODE;

        } catch ( Exception e ) {
            return ResponseCode.ERROR_CODE;
        }
    }

    /**
     * 게시글 갯수 반환 ( 페이징 처리 )
     */
    @Override
    public int getBoardCount() {
        return boardRepository.getBoardCount();
    }

    /**
     * 게시글 리스트 반환
     */
    @Override
    public List<Board> getBoardList(int startIdx, int count) {
        return boardRepository.getBoardList(startIdx, count);
    };

    /**
     * 게시글 상세 정보 반환
     */
    @Override
    public Board getBoardInfo(Long boardId) {
        try {
            boardRepository.addBoardView(boardId);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return boardRepository.getBoardInfo(boardId);
    }

    /**
     * 게시글 수정
     */
    @Override
    public String updateBoard(Board board){
        try {
            board.setUpdateDateTime(LocalDateTime.now());
            boardRepository.updateBoard(board.getBoardId(), board.getTitle(), board.getContent(), board.getUpdateDateTime());
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR_CODE;
        }
    };

    /**
     * 게시글 삭제
     */
    @Override
    public String deleteBoard(Long boardId){
        try {
            boardRepository.deleteBoard(boardId);
            return ResponseCode.SUCCESS_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.ERROR_CODE;
        }
    };
}

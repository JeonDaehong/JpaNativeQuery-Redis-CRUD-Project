package com.example.crudproject.controller;

import com.example.crudproject.common.ResponseCode;
import com.example.crudproject.domain.Board;
import com.example.crudproject.domain.User;
import com.example.crudproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // Validation Check Overloading
    public boolean validationCheck(String title, String content) {
        if (title.equals("") || content.equals("")) return true;
        return false;
    }
    public boolean validationCheck(Long userId) {
        if (userId == null ) return false;
        return true;
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/board/write")
    public String boardWrite(@RequestParam(value = "title") String title,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "userId") Long userId,
                             HttpSession session) {

        // validation Check
        if (validationCheck(title, content)) return ResponseCode.ERROR_CODE;
        if (!validationCheck(userId)) return ResponseCode.LOGIN_ERROR_CODE;

        // Session 끊킬 시 redirect
        User sessionUser = (User) session.getAttribute("loginUser");
        boolean loginSession = sessionUser != null;
        if ( !loginSession ) return "redirect:/loginPage";

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setUserId(userId);

        return boardService.writeBoard(board); // insert

    }

    /**
     * 게시글 수정
     */
    @PostMapping("/board/update")
    public String boardUpdate(@RequestParam(value = "title") String title,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "boardId") Long boardId,
                             HttpSession session) {

        // validation
        if (validationCheck(title, content)) return ResponseCode.ERROR_CODE;

        // Session 끊킬 시 redirect
        User sessionUser = (User) session.getAttribute("loginUser");
        boolean loginSession = sessionUser != null;
        if ( !loginSession ) return "redirect:/loginPage";

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setBoardId(boardId);

        return boardService.updateBoard(board); // update

    }

    /**
     * 게시글 삭제
     */
    @PostMapping("/board/delete")
    public String boardUpdate(@RequestParam(value = "boardId") Long boardId,
                              HttpSession session) {

        // Session 끊킬 시 redirect
        User sessionUser = (User) session.getAttribute("loginUser");
        boolean loginSession = sessionUser != null;
        if ( !loginSession ) return "redirect:/loginPage";

        return boardService.deleteBoard(boardId); // delete

    }

}

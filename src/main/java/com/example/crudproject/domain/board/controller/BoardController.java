package com.example.crudproject.domain.board.controller;

import com.example.crudproject.domain.user.entity.User;
import com.example.crudproject.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boardListPage")
    public String getBoardListPage(HttpSession session, Model model,
                                   @RequestParam(value = "startIdx", defaultValue = "0") int startIdx,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "pagingType", defaultValue = "0") int pagingType) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";
        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        // 페이징 처리
        int count = 10;
        boolean nextPage = false;
        boolean beforePage = false;
        if ( pagingType == 1 ) { // 다음
            startIdx += count;
            page ++;
        } else if ( pagingType == 2 ) { // 이전
            startIdx -= count;
            page --;
        }
        if ( page > 1 ) beforePage = true;

        int totalPageCount = boardService.getBoardCount();
        if ( totalPageCount > startIdx + 10 ) nextPage = true;

        model.addAttribute("boardList", boardService.getBoardList(startIdx, count));
        model.addAttribute("startIdx", startIdx);
        model.addAttribute("page", page);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("beforePage", beforePage);

        return "/boardList";
    }


    @GetMapping("/boardWritePage")
    public String getBoardWritePage(HttpSession session, Model model) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";

        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/boardWrite";
    }


    @GetMapping("/boardInfoPage")
    public ResponseEntity<?> getBoardInfoPage(HttpSession session, Model model,
                                              @RequestParam(value = "boardId", required = false) Long boardId) {


        model.addAttribute("board", boardService.getBoardInfo(boardId));

        return ResponseEntity.ok("/boardInfo");
    }


    @GetMapping("/boardUpdatePage")
    public String getBoardUpdatePage(HttpSession session, Model model,
                                     @RequestParam(value = "boardId", required = false) Long boardId) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";

        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);
        model.addAttribute("board", boardService.getBoardInfo(boardId));

        return "/boardUpdate";
    }
}

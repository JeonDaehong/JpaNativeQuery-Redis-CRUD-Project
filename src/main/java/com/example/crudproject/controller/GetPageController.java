package com.example.crudproject.controller;

import com.example.crudproject.domain.Board;
import com.example.crudproject.domain.User;
import com.example.crudproject.domain.vo.BoardVo;
import com.example.crudproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GetPageController {

    private final BoardService boardService;

    /**
     * 메인 페이지 이동
     */
    @GetMapping ("/")
    public String main(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/main";
    }

    /**
     * 로그인 페이지 이동
     */
    @GetMapping("/loginPage")
    public String loginPage() {
        return "/login";
    }

    /**
     * 회원가입 페이지 이동
     */
    @GetMapping("/joinPage")
    public String joinPage() {
        return "/join";
    }

    /**
     * 내 정보 페이지로 이동
     */
    @GetMapping("/userInfoPage")
    public String userInfo(HttpSession session, Model model) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";
        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/userInfo";
    }

    /**
     * 게시판 리스트 페이지로 이동 ( 페이징 처리 10개씩 )
     */
    @GetMapping("/boardListPage")
    public String boardListPage(HttpSession session, Model model,
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
        boolean nextPage = false;
        boolean beforePage = false;
        if ( pagingType == 1 ) { // 다음
            startIdx += 10;
            page ++;
        } else if ( pagingType == 2 ) { // 이전
            startIdx -= 10;
            page --;
        }
        if ( page > 1 ) beforePage = true;

        int totalPageCount = boardService.getBoardCount();
        if ( totalPageCount > startIdx + 10 ) nextPage = true;

        int count = 10;
        List<Board> dbBoardList = boardService.getBoardList(startIdx, count);
        List<BoardVo> boardList = new ArrayList<>();
        for ( Board board : dbBoardList ) {
            BoardVo boardVo = new BoardVo();
            boardVo.setBoardId(board.getBoardId());
            boardVo.setTitle(board.getTitle());
            boardVo.setContent(board.getContent());
            boardVo.setBoardView(board.getBoardView() + boardService.getBoardViewRedis(board.getBoardId()));
            boardVo.setCreateDateTime(board.getCreateDateTime());
            boardVo.setUpdateDateTime(board.getUpdateDateTime());
            boardVo.setUserId(board.getUserId());
            boardList.add(boardVo);
        }

        model.addAttribute("boardList", boardList);
        model.addAttribute("startIdx", startIdx);
        model.addAttribute("page", page);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("beforePage", beforePage);

        return "/boardList";
    }

    /**
     * 게시글 작성 페이지로 이동
     */
    @GetMapping("/boardWritePage")
    public String boardWritePage(HttpSession session, Model model) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";

        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        return "/boardWrite";
    }


    /**
     * 게시글 상세 정보 페이지로 이동
     */
    @GetMapping("/boardInfoPage")
    public String boardInfoPage(HttpSession session, Model model,
                                @RequestParam(value = "boardId", required = false) Long boardId,
                                @RequestParam(value = "userId", required = false) Long userId ) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";

        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        Board dbBoard = boardService.getBoardInfo(boardId);

        BoardVo board = new BoardVo();
        board.setBoardId(dbBoard.getBoardId());
        board.setTitle(dbBoard.getTitle());
        board.setContent(dbBoard.getContent());
        board.setBoardView(dbBoard.getBoardView() + boardService.getBoardViewRedisIncrement(dbBoard.getBoardId()));
        board.setCreateDateTime(dbBoard.getCreateDateTime());
        board.setUpdateDateTime(dbBoard.getUpdateDateTime());
        board.setUserId(dbBoard.getUserId());

        model.addAttribute("board", board);

        // 수정, 삭제 버튼 활성화를 위한 boolean
        boolean fixPossible = false;
        if (Objects.equals(user.getUserId(), userId)) fixPossible = true;
        model.addAttribute("fixPossible", fixPossible);

        return "/boardInfo";
    }

    /**
     * 게시글 수정 페이지로 이동
     */
    @GetMapping("/boardUpdatePage")
    public String boardUpdatePage(HttpSession session, Model model,
                                  @RequestParam(value = "boardId", required = false) Long boardId) {

        // Session 끊킬 시 redirect
        User user = (User) session.getAttribute("loginUser");
        boolean loginSession = user != null;
        if ( !loginSession ) return "redirect:/loginPage";

        model.addAttribute("user", user);
        model.addAttribute("loginSession", loginSession);

        Board board = boardService.getBoardInfo(boardId);
        model.addAttribute("board", board);

        return "/boardUpdate";
    }

}

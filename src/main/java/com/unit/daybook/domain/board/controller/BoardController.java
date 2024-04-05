package com.unit.daybook.domain.board.controller;

import com.unit.daybook.domain.board.dto.BoardAddReqDto;
import com.unit.daybook.domain.board.dto.BoardAddResDto;
import com.unit.daybook.domain.board.service.BoardService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId) {
        return "단일 조회 정보 " + boardId;
    }

    @GetMapping("/boards")
    public String getBoards() {
        return "목록 조회 정보";
    }


    @PostMapping
    public BoardAddResDto addBoard(@RequestBody BoardAddReqDto boardAddReqDto) {
        Long memberId = 1L; // TODO 인증
        return boardService.addBoard(boardAddReqDto, memberId);

    }

    @PostMapping("/{boardId}")
    public String modifyBoard(@PathVariable("boardId") Long boardId) {
        return "수정 성공";
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        return "삭제 성공";
    }
}

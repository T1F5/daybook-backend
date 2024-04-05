package com.unit.daybook.domain.board.controller;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.AddBoardResponseDto;
import com.unit.daybook.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId) {
        return "단일 조회 정보 " + boardId;
    }

    @GetMapping("/boards")
    public String getBoards() {
        return "목록 조회 정보";
    }


    @PostMapping
    public AddBoardResponseDto addBoard(@RequestBody AddBoardRequestDto addBoardRequestDto) {
        Long memberId = 1L; // TODO 인증
        return boardService.addBoard(addBoardRequestDto, memberId);

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

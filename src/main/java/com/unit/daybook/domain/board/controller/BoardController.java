package com.unit.daybook.domain.board.controller;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.AddBoardResponseDto;
import com.unit.daybook.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public AddBoardResponseDto getBoard(@PathVariable("boardId") Long boardId) {
        return boardService.getBoard(boardId);
    }

    @GetMapping("/boards")
    public List<AddBoardResponseDto> getMyBoards() {
        // 사용자가 작성한 일지 목록 조회
        Long memberId = 1L; // TODO 인증
        return boardService.getMyBoards(memberId);
    }

    // TODO 사용자가 보지 않은 글 중에서 랜덤 3개 골라 주기
    @GetMapping("/random")
    public List<AddBoardResponseDto> getRandomBoards() {
        // 사용자가 작성한 일지 목록 조회
        // TODO api 호출 시점마다 랜덤x. 12시에 사용자가 읽지 않은 글을 today_user_table에 저장한다. 전날 건 삭제
        // todo api는 today_user_table 에 있는 정보를 read. 계산 x
        Long memberId = 1L; // TODO 인증
        return boardService.getRandomBoards(memberId);
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

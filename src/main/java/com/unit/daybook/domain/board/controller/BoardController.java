package com.unit.daybook.domain.board.controller;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.*;
import com.unit.daybook.domain.board.service.BoardService;
import com.unit.daybook.domain.common.annotation.LoginUsers;
import com.unit.daybook.global.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public FindOneBoardResponse getBoard(
        @PathVariable("boardId") Long boardId
    ) {
        return boardService.getBoard(boardId);
    }

    /**
     * 사용자가 작성한 일지 목록 조회
     */
    @GetMapping("/boards")
    public List<FindBoardResponse> getMyBoards(@LoginUsers CustomUserDetails userDetails) {
        FindBoardListResponse tmp = boardService.getMyBoards(userDetails.getMemberId());
        return tmp.boards();
    }

    /**
     * 사용자가 보지 않은 글 중에서 랜덤 3개 골라 주기
     * 밤 12시에 적재된 사용자가 읽지 않은 글을 조회
     * 만약 배치 후 가입한 사용자라면, 자기가 쓰지 않은 최신글
     */
    @GetMapping("/random")
    public List<BoardResponseDto> getRandomBoards(@LoginUsers CustomUserDetails userDetails) {

		return boardService.getRandomBoards(userDetails.getMemberId());
    }

    @PostMapping
    public BoardTmpResponse addBoard(
        @RequestBody AddBoardRequestDto addBoardRequestDto,
        @LoginUsers CustomUserDetails userDetails)
    {
        BoardResponseDto board = boardService.addBoard(addBoardRequestDto, userDetails.getMemberId());
        return boardService.getBoardWithHashTag(board.boardId());
    }

    // @PostMapping("/{boardId}")
    // public BoardTmpResponse modifyBoard(@PathVariable("boardId") Long boardId, @RequestBody AddBoardRequestDto addBoardRequestDto) {
    //     return boardService.modifyBoard(boardId, addBoardRequestDto);
    // }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public String test() {
        boardService.batchReadBoard();
        return "test";
    }
}

package com.unit.daybook.domain.board.controller;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.AddBoardResponseDto;
import com.unit.daybook.domain.board.dto.response.BoardTmpResponse;
import com.unit.daybook.domain.board.entity.ReadBoard;
import com.unit.daybook.domain.board.service.BoardService;
import com.unit.daybook.domain.common.annotation.LoginUsers;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.global.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public AddBoardResponseDto getBoard(@PathVariable("boardId") Long boardId) {
        return boardService.getBoard(boardId);
    }

    /**
     * 사용자가 작성한 일지 목록 조회
     */
    @GetMapping("/boards")
    public List<AddBoardResponseDto> getMyBoards(@LoginUsers CustomUserDetails userDetails) {
        return boardService.getMyBoards(userDetails.getMemberId());
    }

    /**
     * 사용자가 보지 않은 글 중에서 랜덤 3개 골라 주기
     * 밤 12시에 적재된 사용자가 읽지 않은 글을 조회
     * 만약 배치 후 가입한 사용자라면, 자기가 쓰지 않은 최신글
     */
    @GetMapping("/random")
    public List<AddBoardResponseDto> getRandomBoards(@LoginUsers CustomUserDetails userDetails) {
        List<AddBoardResponseDto> result = boardService.getRandomBoards(userDetails.getMemberId());

        return result;
    }

    @PostMapping
    public BoardTmpResponse addBoard(@RequestBody AddBoardRequestDto addBoardRequestDto, @LoginUsers CustomUserDetails userDetails) {
        AddBoardResponseDto board = boardService.addBoard(addBoardRequestDto, userDetails.getMemberId());
        return boardService.getBoardWithHashTag(board.boardId());
    }

    @PostMapping("/{boardId}")
    public BoardTmpResponse modifyBoard(@PathVariable("boardId") Long boardId, @RequestBody AddBoardRequestDto addBoardRequestDto) {
        return boardService.modifyBoard(boardId, addBoardRequestDto);
    }

    @DeleteMapping("/{boardId}")
    public Map<String, Long> deleteBoard(@PathVariable("boardId") Long boardId) {
        return boardService.deleteBoard(boardId);
    }

    @GetMapping("/test")
    public String test() {
        boardService.batchReadBoard();
        return "test";
    }
}

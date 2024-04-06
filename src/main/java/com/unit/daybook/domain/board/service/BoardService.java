package com.unit.daybook.domain.board.service;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.AddBoardResponseDto;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.repository.BoardRepository;

import com.unit.daybook.domain.board.repository.BoardRepositoryImpl;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardRepositoryImpl boardRepositoryImpl;
    private final MemberRepository memberRepository;

    public AddBoardResponseDto addBoard(AddBoardRequestDto addBoardRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException(memberId + "not found"));
        if (addBoardRequestDto.respectBoardId() != null) {
            Board respectBoard = boardRepository.findById(addBoardRequestDto.respectBoardId()).orElseThrow(() -> new RuntimeException(addBoardRequestDto.respectBoardId() + "not found"));
            respectBoard.plusRespect();
            boardRepository.save(respectBoard);
        }
        // 글의 카운트 올리기.. redis..?

        return AddBoardResponseDto.from(boardRepository.save(Board.createBoard(addBoardRequestDto, member)));
    }


    public AddBoardResponseDto getBoard(Long boardId) {
        return AddBoardResponseDto.from(
                boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(boardId + "not found")));
    }

    public List<AddBoardResponseDto> getMyBoards(Long memberId) {
        // TODO 페이지네이션 - 스와이프 방식?
        return boardRepositoryImpl.findBoardsByMemberId(memberId)
                .stream()
                .map(AddBoardResponseDto::from)
                .toList();
    }

    public List<AddBoardResponseDto> getRandomBoards(Long memberId) {
        return null;
    }
}

package com.unit.daybook.domain.board.service;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.AddBoardResponseDto;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    private final BoardRepository boardRepository;

    @Transactional
    public AddBoardResponseDto addBoard(AddBoardRequestDto addBoardRequestDto, Long memberId) {
        return AddBoardResponseDto.from(boardRepository.save(Board.createBoard(addBoardRequestDto)));
    }

}

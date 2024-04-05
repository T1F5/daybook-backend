package com.unit.daybook.domain.board.service;

import com.unit.daybook.domain.board.dto.BoardAddReqDto;
import com.unit.daybook.domain.board.dto.BoardAddResDto;
import com.unit.daybook.domain.board.entity.BoardEntity;
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
    public BoardAddResDto addBoard(BoardAddReqDto boardAddReqDto, Long memberId) {
        BoardEntity entity = boardRepository.save(boardAddReqDto.toEntity());
        return BoardAddResDto.builder()
                .entity(entity)
                .build();
    }
}

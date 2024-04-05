package com.unit.daybook.domain.board.dto.response;

import com.unit.daybook.domain.board.entity.Board;

public record AddBoardResponseDto(
        Long boardId,
        String content,
        Long respectBoardId
) {

    public static AddBoardResponseDto from(Board board) {
        return new AddBoardResponseDto(board.getBoardId(), board.getContent(), board.getRespectBoardId());
    }

}

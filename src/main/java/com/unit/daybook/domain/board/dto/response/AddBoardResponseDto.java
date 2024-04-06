package com.unit.daybook.domain.board.dto.response;

import com.unit.daybook.domain.board.entity.Board;

import java.util.List;

public record AddBoardResponseDto(
        Long boardId,
        String content,
        Long respectBoardId,
        Long authorId,

        String category,
        Long hearts,
        List<String> hashtags,
        String paperType

) {

    public static AddBoardResponseDto from(Board board) {
        List<String> hashContents = board.getHashtags().stream().map(hashtag -> hashtag.getContent()).toList();
        return new AddBoardResponseDto(board.getBoardId(), board.getContent(), board.getRespectBoardId(), board.getMemeber().getId(), board.getCategory(), board.getHearts(), hashContents, board.getPaperType());
    }
}

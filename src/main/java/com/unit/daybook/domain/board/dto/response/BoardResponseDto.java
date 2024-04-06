package com.unit.daybook.domain.board.dto.response;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.entity.Hashtag;

import java.time.LocalDateTime;
import java.util.List;

public record BoardResponseDto(
        Long boardId,
        String content,
        Long respectBoardId,
        Long authorId,
        String category,
        Long hearts,
        List<String> hashtags,
        String paperType,
        LocalDateTime createdAt
) {

    public static BoardResponseDto from(Board board) {
        List<String> hashContents = board.getHashtags().stream().map(Hashtag::getContent).toList();
        return new BoardResponseDto(
            board.getBoardId(),
            board.getContent(),
            board.getRespectBoardId(),
            board.getMemeber().getId(),
            board.getCategory(),
            board.getHearts(),
            hashContents,
            board.getPaperType(),
            board.getCreatedAt()
        );
    }
}

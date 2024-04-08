package com.unit.daybook.domain.board.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record BoardTmpResponse (
    Long boardId,
    String content,
    Long respectBoardId,
    Long authorId,
    String category,
    Long hearts,
    String papaerType,
    List<String> hashtags,
    LocalDateTime createdAt
) {

    public static BoardTmpResponse of(FindOneBoardResponse dto, List<String> hashtags) {
        return new BoardTmpResponse(
            dto.boardId(),
            dto.content(),
            dto.respectBoardId(),
            dto.authorId(),
            dto.category(),
            dto.hearts(),
            dto.paperType(),
            hashtags,
            dto.createdAt()
        );
    }
}

package com.unit.daybook.domain.board.dto.response;

import java.time.LocalDateTime;

import com.unit.daybook.domain.board.entity.Board;

public record FindBoardResponse(
	Long boardId,
	String content,
	Long respectBoardId,
	Long authorId,
	String category,
	Long hearts,
	String paperType,
	LocalDateTime createdAt
) {
	public static FindBoardResponse from(Board board) {
		return new FindBoardResponse(
			board.getBoardId(),
			board.getContent(),
			board.getRespectBoardId(),
			board.getMemeber().getId(),
			board.getCategory(),
			board.getHearts(),
			board.getPaperType(),
			board.getCreatedAt()
		);
	}
}

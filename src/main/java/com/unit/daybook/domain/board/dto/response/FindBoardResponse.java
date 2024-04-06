package com.unit.daybook.domain.board.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unit.daybook.domain.board.entity.Board;

public record FindBoardResponse(
	Long boardId,
	String content,
	Long respectBoardId,
	Long authorId,
	String category,

	Long hearts,
	String paperType,
	LocalDateTime createdAt,
	List<String> hashtags
) {
	public static FindBoardResponse from(Board board) {
		return new FindBoardResponse(
			board.getBoardId(),
			board.getContent(),
			board.getRespectBoardId(),
			board.getMember().getId(),
			board.getCategory(),
			board.getHearts(),
			board.getPaperType(),
			board.getCreatedAt(),
			board.getHashtags().stream().map(h->h.getContent()).toList()
		);
	}
}

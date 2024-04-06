package com.unit.daybook.domain.board.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.entity.Hashtag;
import com.unit.daybook.domain.comment.dto.response.FindOneCommentResponse;
import com.unit.daybook.domain.reaction.dto.response.ReactionTypeAndCount;
import com.unit.daybook.domain.reaction.entity.Reaction;

public record FindOneBoardResponse(
	Long boardId,
	String content,
	Long respectBoardId,
	Long authorId,

	String category,
	Long hearts,
	List<String> hashtags,
	String paperType,
	List<ReactionTypeAndCount> reactions,
	List<FindOneCommentResponse> comments,
	LocalDateTime createdAt
) {
	public static FindOneBoardResponse of(Board board, List<ReactionTypeAndCount> reactions, List<FindOneCommentResponse> comments) {
		List<String> hashContents = board.getHashtags().stream().map(Hashtag::getContent).toList();
		Long heartsFromReactions = reactions.stream().mapToLong(ReactionTypeAndCount::count).sum();
		return new FindOneBoardResponse(
			board.getBoardId(),
			board.getContent(),
			board.getRespectBoardId(),
			board.getMemeber().getId(),
			board.getCategory(),
			heartsFromReactions,
			hashContents,
			board.getPaperType(),
			reactions,
			comments,
			board.getCreatedAt()
		);
	}
}

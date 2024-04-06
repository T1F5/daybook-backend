package com.unit.daybook.domain.reaction.dto.response;

import com.unit.daybook.domain.reaction.entity.Reaction;
import com.unit.daybook.domain.reaction.entity.ReactionType;

public record ReactionCreateResponse(
	Long reactionId,
	ReactionType reactionType,
	Long memberId,
	Long boardId
) {
	public static ReactionCreateResponse from(Reaction reaction) {
		return new ReactionCreateResponse(
			reaction.getId(),
			reaction.getReactionType(),
			reaction.getMember().getId(),
			reaction.getBoard().getBoardId()
		);
	}

}
package com.unit.daybook.domain.reaction.dto.request;

import com.unit.daybook.domain.reaction.entity.ReactionType;

public record ReactionCreateRequest(
	Long boardId,
	ReactionType reactionType
) {
}

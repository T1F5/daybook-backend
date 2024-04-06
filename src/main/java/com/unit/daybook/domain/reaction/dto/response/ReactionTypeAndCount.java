package com.unit.daybook.domain.reaction.dto.response;

import com.unit.daybook.domain.reaction.entity.ReactionType;

public record ReactionTypeAndCount(
	ReactionType reactionType,
	Long count
) {
}

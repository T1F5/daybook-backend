package com.unit.daybook.domain.comment.dto.response;

import com.unit.daybook.domain.comment.entity.Comment;

public record FindOneCommentResponse(
	Long commentId,
	String content
) {
	public static FindOneCommentResponse from(Comment comment) {
		return new FindOneCommentResponse(comment.getCommentId(), comment.getContent());
	}
}

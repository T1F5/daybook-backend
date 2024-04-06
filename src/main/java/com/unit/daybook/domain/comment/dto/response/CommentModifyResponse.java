package com.unit.daybook.domain.comment.dto.response;

import com.unit.daybook.domain.comment.entity.Comment;

public record CommentModifyResponse(
	String content,
	Long commentId
) {
	public static CommentModifyResponse from(Comment comment) {
		return new CommentModifyResponse(comment.getContent(), comment.getCommentId());
	}
}

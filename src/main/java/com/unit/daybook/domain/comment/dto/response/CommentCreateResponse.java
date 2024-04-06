package com.unit.daybook.domain.comment.dto.response;

import com.unit.daybook.domain.comment.entity.Comment;

public record CommentCreateResponse(
	Long commentId,
	String content,
	Long boardId,
	Long memberId
) {
	public static CommentCreateResponse from(Comment comment) {
		return new CommentCreateResponse(
			comment.getCommentId(),
			comment.getContent(),
			comment.getBoard().getBoardId(),
			comment.getMember().getId()
		);
	}
}

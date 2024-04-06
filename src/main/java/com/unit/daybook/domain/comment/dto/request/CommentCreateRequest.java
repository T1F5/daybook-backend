package com.unit.daybook.domain.comment.dto.request;

public record CommentCreateRequest (
	String content,
	Long boardId
) {
}

package com.unit.daybook.domain.comment.repository;

import java.util.List;

import com.unit.daybook.domain.comment.dto.response.FindOneCommentResponse;
import com.unit.daybook.domain.comment.entity.Comment;

public interface CommentRepositoryCustom {
	List<Comment> findCommentByBoard(Long boardId);
}

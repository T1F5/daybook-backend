package com.unit.daybook.domain.comment.repository;

import static com.unit.daybook.domain.board.entity.QBoard.*;
import static com.unit.daybook.domain.comment.entity.QComment.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.comment.entity.Comment;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Comment> findCommentByBoard(Long boardId) {
		return jpaQueryFactory.selectFrom(comment)
			.leftJoin(comment.board, board)
			.fetchJoin()
			.where(board.boardId.eq(boardId))
			.orderBy(comment.createdAt.desc())
			.fetch();
	}
}

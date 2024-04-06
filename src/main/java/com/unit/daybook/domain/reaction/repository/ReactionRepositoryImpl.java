package com.unit.daybook.domain.reaction.repository;

import static com.unit.daybook.domain.reaction.entity.QReaction.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.reaction.entity.Reaction;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReactionRepositoryImpl implements ReactionRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Reaction findReactionByMemberAndBoard(Member member, Board board) {
		return jpaQueryFactory.selectFrom(reaction)
			.where(
				reaction.board.boardId.eq(board.getBoardId()),
				reaction.member.id.eq(member.getId()))
			.fetchOne();
	}
}

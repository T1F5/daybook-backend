package com.unit.daybook.domain.reaction.repository;

import static com.unit.daybook.domain.reaction.entity.QReaction.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.reaction.dto.response.ReactionTypeAndCount;
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

	@Override
	public List<ReactionTypeAndCount> findAllByBoardGroupByReactionType(Board board) {
		return jpaQueryFactory
			.select(Projections.constructor(ReactionTypeAndCount.class, reaction.reactionType, reaction.count()))
			.from(reaction)
			.where(reaction.board.eq(board))
			.groupBy(reaction.reactionType)
			.fetch();
	}

}

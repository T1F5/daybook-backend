package com.unit.daybook.domain.reaction.repository;

import java.util.List;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.reaction.dto.response.ReactionTypeAndCount;
import com.unit.daybook.domain.reaction.entity.Reaction;

public interface ReactionRepositoryCustom {

	Reaction findReactionByMemberAndBoard(Member member, Board board);
	List<ReactionTypeAndCount> findAllByBoardGroupByReactionType(Board board);
}

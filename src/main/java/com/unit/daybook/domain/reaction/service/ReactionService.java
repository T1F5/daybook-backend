package com.unit.daybook.domain.reaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.repository.BoardRepository;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.repository.MemberRepository;
import com.unit.daybook.domain.reaction.dto.request.ReactionCreateRequest;
import com.unit.daybook.domain.reaction.dto.response.ReactionCreateResponse;
import com.unit.daybook.domain.reaction.entity.Reaction;
import com.unit.daybook.domain.reaction.repository.ReactionRepository;
import com.unit.daybook.global.error.exception.CustomException;
import com.unit.daybook.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReactionService {

	private final ReactionRepository reactionRepository;
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;

	public ReactionCreateResponse createReaction(ReactionCreateRequest request, Long memberId) {
		final Member member = findMemberById(memberId);
		Board board = boardRepository.findById(request.boardId())
			.orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

		validateReactionMemberMatching(member, board);

		Reaction reaction = Reaction.createReaction(request.reactionType(), member, board);
		return ReactionCreateResponse.from(reactionRepository.save(reaction));
	}

	private void validateReactionMemberMatching(Member member, Board board) {
		Reaction reaction = reactionRepository.findReactionByMemberAndBoard(member, board);
		if (reaction != null) {
			throw new CustomException(ErrorCode.REACTION_EXISTS);
		}
	}

	private Member findMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

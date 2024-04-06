package com.unit.daybook.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.repository.BoardRepository;
import com.unit.daybook.domain.comment.dto.request.CommentCreateRequest;
import com.unit.daybook.domain.comment.dto.request.CommentModifyRequest;
import com.unit.daybook.domain.comment.dto.response.CommentCreateResponse;
import com.unit.daybook.domain.comment.dto.response.CommentModifyResponse;
import com.unit.daybook.domain.comment.entity.Comment;
import com.unit.daybook.domain.comment.repository.CommentRepository;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.repository.MemberRepository;
import com.unit.daybook.global.error.exception.CustomException;
import com.unit.daybook.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;

	public CommentCreateResponse createComment(CommentCreateRequest request, Long memberId) {
		final Member member = findMemberById(memberId);
		Board board = boardRepository.findById(request.boardId())
			.orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
		Comment comment = Comment.createComment(request.content(), member, board);
		return CommentCreateResponse.from(commentRepository.save(comment));
	}

	private Member findMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}

	public CommentModifyResponse updateComment(CommentModifyRequest request, Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
		comment.updateContent(request.content());
		return CommentModifyResponse.from(comment);
	}
}

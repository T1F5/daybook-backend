package com.unit.daybook.domain.board.repository;

import java.util.List;

import com.unit.daybook.domain.board.entity.Board;

public interface BoardRepositoryCustom {
	List<Board> findBoardsByMemberId(Long memberId);
	List<Board> findBoardInBoardIds(List<Long> boardIds);

	List<Board> findNotReadBoardsByMemberId(Long memberId, List<Board> alreadyReadBoards);

	List<Board> findCurrentBoards(Long memberId);
}

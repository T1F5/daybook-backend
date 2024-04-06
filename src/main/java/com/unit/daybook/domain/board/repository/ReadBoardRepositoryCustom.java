package com.unit.daybook.domain.board.repository;

import java.util.List;

import com.unit.daybook.domain.board.entity.Board;

public interface ReadBoardRepositoryCustom {
	List<Board> findBoardsByMemberId(Long memberId);

	List<Long> findTodayBoardsByMemberId(Long memberId);
}

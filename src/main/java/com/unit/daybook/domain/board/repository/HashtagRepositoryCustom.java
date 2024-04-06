package com.unit.daybook.domain.board.repository;

import java.util.List;

public interface HashtagRepositoryCustom {
	List<String> findAllByBoardId(Long boardId);
}

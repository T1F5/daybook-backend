package com.unit.daybook.domain.board.dto.response;

import java.util.List;

import com.unit.daybook.domain.board.entity.Board;

public record FindBoardListResponse (
	List<FindBoardResponse> boards
) {
	public static FindBoardListResponse from(List<Board> board) {
		return new FindBoardListResponse(board.stream().map(FindBoardResponse::from).toList());
	}
}

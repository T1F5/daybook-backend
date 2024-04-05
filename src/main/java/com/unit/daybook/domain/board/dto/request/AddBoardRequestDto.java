package com.unit.daybook.domain.board.dto.request;


public record AddBoardRequestDto(String content, Long respectBoardId, String category) {
}
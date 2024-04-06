package com.unit.daybook.domain.board.dto.request;


import java.util.List;

public record AddBoardRequestDto(String content, Long respectBoardId, String category, Long hearts, List<String> hashtags, String paperType) {
}
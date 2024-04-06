package com.unit.daybook.domain.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardTmpResponse {
    private Long boardId;
    private String content;
    private Long respectBoardId;
    private Long authorId;
    private String category;
    private Long hearts;
    private String papaerType;
    private  List<String> hashtags;
    private LocalDateTime createdAt;

    @Builder
    public BoardTmpResponse(AddBoardResponseDto dto, List<String> hashtags) {
        this.boardId = dto.boardId();
        this.content = dto.content();
        this.respectBoardId = dto.respectBoardId();
        this.authorId = dto.authorId();
        this.category = dto.category();
        this.hearts = dto.hearts();
        this.papaerType = dto.paperType();
        this.hashtags = hashtags;
        this.createdAt = dto.createdAt();
    }
}

package com.unit.daybook.domain.board.dto;

import com.unit.daybook.domain.board.entity.BoardEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardAddResDto {
    private Long boardId;
    private String content;
    private Long respectBoardId;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public BoardAddResDto(BoardEntity entity) {
        this.boardId = entity.getBoardId();
        this.content = entity.getContent();
        this.respectBoardId = entity.getRespectBoardId();
        this.createdAt = null;
        this.modifiedAt = null;
    }

}

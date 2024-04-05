package com.unit.daybook.domain.board.dto;

import com.unit.daybook.domain.board.entity.BoardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BoardAddReqDto {
    private String content;
    private Long respectBoardId;


    public BoardEntity toEntity() {
        BoardEntity entity = new BoardEntity();
        entity.setContent(content);
        entity.setRespectBoardId(respectBoardId);
        return entity;
    }
}

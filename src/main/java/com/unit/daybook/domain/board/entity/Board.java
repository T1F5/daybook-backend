package com.unit.daybook.domain.board.entity;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column
    private String content;

    @Column(name = "respect_board_id")
    private Long respectBoardId;


    @Builder(access = AccessLevel.PRIVATE)
    public Board(Long boardId, String content, Long respectBoardId) {
        this.boardId = boardId;
        this.content = content;
        this.respectBoardId = respectBoardId;
    }

    public static Board createBoard(AddBoardRequestDto addBoardRequestDto) {
        return Board.builder()
                .content(addBoardRequestDto.content())
                .respectBoardId(addBoardRequestDto.respectBoardId())
                .build();
    }

}

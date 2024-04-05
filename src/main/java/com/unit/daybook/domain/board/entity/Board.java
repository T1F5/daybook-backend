package com.unit.daybook.domain.board.entity;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.common.model.BaseTimeEntity;
import com.unit.daybook.domain.member.domain.Member;
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

    @Column
    private String category;

    @Column(name = "respect_board_id")
    private Long respectBoardId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memeber;

    @Builder(access = AccessLevel.PRIVATE)
    public Board(Long boardId, String content, Long respectBoardId, Member member, String category) {
        this.boardId = boardId;
        this.content = content;
        this.respectBoardId = respectBoardId;
        this.memeber = member;
        this.category = category;
    }

    public static Board createBoard(AddBoardRequestDto addBoardRequestDto, Member member) {
        return Board.builder()
                .content(addBoardRequestDto.content())
                .respectBoardId(addBoardRequestDto.respectBoardId())
                .member(member)
                .category(addBoardRequestDto.category())
                .build();
    }

}

package com.unit.daybook.domain.board.entity;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.common.model.BaseTimeEntity;
import com.unit.daybook.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


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

    @Column
    private String paperType;

    @Column
    private Long hearts;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Hashtag> hashtags = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Board(Long boardId, String content, Long respectBoardId, Member member, String category, Long hearts, String paperType) {
        this.boardId = boardId;
        this.content = content;
        this.respectBoardId = respectBoardId;
        this.memeber = member;
        this.category = category;
        this.hearts = hearts;
        this.paperType = paperType;
    }

    public static Board createBoard(AddBoardRequestDto addBoardRequestDto, Member member) {
        return Board.builder()
                .content(addBoardRequestDto.content())
                .respectBoardId(addBoardRequestDto.respectBoardId())
                .member(member)
                .paperType(addBoardRequestDto.paperType())
                .category(addBoardRequestDto.category())
                .hearts(0L)
                .build();
    }

    public void plusRespect() {
        this.hearts += 1;
    }

    public void modifyBoard(AddBoardRequestDto addBoardRequestDto) {
        this.content = addBoardRequestDto.content();
        this.category = addBoardRequestDto.category();
    }

}

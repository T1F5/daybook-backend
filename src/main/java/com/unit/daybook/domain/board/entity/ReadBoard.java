package com.unit.daybook.domain.board.entity;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.common.model.BaseTimeEntity;
import com.unit.daybook.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "read_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "read_board_id")
    private Long readBoardId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder(access = AccessLevel.PRIVATE)
    public ReadBoard(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

    public static ReadBoard createReadBoard(Member member, Board board) {
        return ReadBoard.builder()
                .member(member)
                .board(board)
                .build();
    }

}
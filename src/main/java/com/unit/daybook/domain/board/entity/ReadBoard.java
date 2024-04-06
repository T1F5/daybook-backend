package com.unit.daybook.domain.board.entity;

import com.unit.daybook.domain.common.model.BaseTimeEntity;
import com.unit.daybook.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "read_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ReadBoard extends BaseTimeEntity {

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

}
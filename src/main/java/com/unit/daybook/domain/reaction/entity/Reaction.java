package com.unit.daybook.domain.reaction.entity;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.member.domain.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "reaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder(access = AccessLevel.PRIVATE)
    private Reaction(ReactionType reactionType, Member member, Board board) {
        this.reactionType = reactionType;
        this.member = member;
        this.board = board;
    }

    public static Reaction createReaction(
        ReactionType reactionType, Member member, Board board
    ) {
        return Reaction.builder()
            .reactionType(reactionType)
            .member(member)
            .board(board)
            .build();
    }

    public void updateReaction(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
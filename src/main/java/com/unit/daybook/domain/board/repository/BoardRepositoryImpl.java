package com.unit.daybook.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.entity.QBoard;
import com.unit.daybook.domain.member.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl  implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<Board> findBoardsByMemberId(Long memberId) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;

        List<Board> boards = queryFactory
                .select(board)
                .from(board)
                .join(board.memeber, member).fetchJoin()
                .where(
                        member.id.eq(memberId)
                )
                .fetch();

        return boards;
    }
}

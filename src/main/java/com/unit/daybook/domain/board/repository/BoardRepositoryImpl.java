package com.unit.daybook.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.unit.daybook.domain.board.entity.QBoard.board;
import static com.unit.daybook.domain.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<Board> findBoardsByMemberId(Long memberId) {

        return queryFactory
                .select(board)
                .from(board)
                .join(board.memeber, member).fetchJoin()
                .where(
                        member.id.eq(memberId)
                )
                .fetch();

    }

    public List<Board> findNotReadBoardsByMemberId(Long memberId, List<Long> aleadyReadBoardIds) {
        return queryFactory
                .select(board)
                .from(board)
                .join(board.memeber, member).fetchJoin()
                .where(
                        member.id.eq(memberId)
                                .and(board.boardId.notIn(aleadyReadBoardIds))
                )
                .fetch();

    }

    public List<Board> findBoardInBoardIds(List<Long> todayBoards) {
        return queryFactory
                .select(board)
                .from(board)
                .where(
                        (board.boardId.in(todayBoards))
                )
                .fetch();

    }
}

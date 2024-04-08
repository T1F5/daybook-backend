package com.unit.daybook.domain.board.repository;

import static com.unit.daybook.domain.board.entity.QBoard.*;
import static com.unit.daybook.domain.member.domain.QMember.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<Board> findBoardsByMemberId(Long memberId) {

        return queryFactory
                .selectFrom(board)
                .leftJoin(board.member, member)
                .fetchJoin()
                .where(
                        member.id.eq(memberId)
                )
                .fetch();

    }

    public List<Board> findNotReadBoardsByMemberId(Long memberId, List<Board> alreadyReadBoardIds) {
        return queryFactory
                .select(board)
                .from(board)
                .join(board.member, member).fetchJoin()
                .where(
                    board.notIn(alreadyReadBoardIds)
                )
                .fetch();

    }

    public List<Board> findBoardInBoardIds(List<Long> todayBoardIds) {
        return queryFactory
                .select(board)
                .from(board)
                .where(
                        board.boardId.in(todayBoardIds)
                )
                .fetch();

    }

    public List<Board> findBoardInWith(List<Long> todayBoards) {
        return queryFactory
                .select(board)
                .from(board)
                .where(
                        board.boardId.in(todayBoards)
                )
                .fetch();

    }


    public List<Board> findCurrentBoards(Long memberId) {
        ArrayList<Long> tmps = new ArrayList<>();
        tmps.add(memberId);
        return queryFactory
                .selectFrom(board)
                .where(board.member.id.notIn(tmps))
                .fetch();
    }
}

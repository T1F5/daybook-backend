package com.unit.daybook.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.unit.daybook.domain.board.entity.QBoard.board;
import static com.unit.daybook.domain.board.entity.QHashtag.hashtag;
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
                        // member.id.eq(memberId)
                                //.and(
                                        board.boardId.notIn(aleadyReadBoardIds)
                                        //)
                )
                .fetch();

    }

    public List<Board> findBoardInBoardIds(List<Long> todayBoards) {
        return queryFactory
                .select(board)
                .from(board)
                .where(
                        board.boardId.in(todayBoards)
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

    public List<Board> findBoardWithHashtag(Long boardId) {
//        return queryFactory
//                .selectFrom(board)
//                .join(board.hashtags, hashtag).fetchJoin()
//                .where(board.boardId.eq(boardId))
//                .fetch();

        return queryFactory
                .selectFrom(board)
                .innerJoin(board.hashtags, hashtag)
                .where(board.boardId.eq(boardId))
                .fetch();

    }

    public List<Board> findCurrentBoards(Long memberId) {
        ArrayList<Long> tmps = new ArrayList<>();
        tmps.add(memberId);
        return queryFactory
                .selectFrom(board)
                .where(board.memeber.id.notIn(tmps))
                .fetch();
    }
}

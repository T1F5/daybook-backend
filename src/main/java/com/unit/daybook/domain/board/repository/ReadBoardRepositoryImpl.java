package com.unit.daybook.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.entity.ReadBoard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.unit.daybook.domain.board.entity.QBoard.board;
import static com.unit.daybook.domain.board.entity.QReadBoard.readBoard;
import static com.unit.daybook.domain.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class ReadBoardRepositoryImpl implements ReadBoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<Board> findBoardsByMemberId(Long memberId) {

        return queryFactory
                .select(readBoard.board)
                .from(readBoard)
                .innerJoin(readBoard.member, member)
                .where(
                        member.id.eq(memberId)
                )
                .fetch();
    }

    public List<Long> findTodayBoardsByMemberId(Long memberId) {
        LocalDate currentDate = LocalDateTime.now().toLocalDate(); // 현재 날짜의 일자만 추출

        return queryFactory
                .select(readBoard.board.boardId)
                .from(readBoard)
                .innerJoin(readBoard.board, board)
                .on(readBoard.board.boardId.eq(board.boardId))
                .innerJoin(readBoard.member, member)
                .on(readBoard.member.id.eq(member.id))
                .where(
                        member.id.eq(memberId)
                                .and(readBoard.createdAt.between(
                                    currentDate.atStartOfDay(),
                                    currentDate.atStartOfDay().plusDays(1).minusNanos(1))
                ))
                .fetch();
    }
}

package com.unit.daybook.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
public class ReadBoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<Long> findBoardsByMemberId(Long memberId) {

        List<Long> boards = queryFactory
                .select(readBoard.board.boardId)
                .from(readBoard)
                .innerJoin(readBoard.member, member)
                .where(
                        member.id.eq(memberId)
                )
                .fetch();

        return boards;
    }

    public List<Long> findTodayBoardsByMemberId(Long memberId) {
        LocalDate currentDate = LocalDateTime.now().toLocalDate(); // 현재 날짜의 일자만 추출

        return queryFactory
                .select(readBoard.readBoardId)
                .from(readBoard)
                .innerJoin(readBoard.board, board)
                .innerJoin(readBoard.member, member)
                .where(
                        member.id.eq(memberId)
                                .and(readBoard.createdAt.between(currentDate.atStartOfDay(), currentDate.atStartOfDay().plusDays(1).minusNanos(1))
                ))
                .fetch();
    }
}

package com.unit.daybook.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unit.daybook.domain.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.unit.daybook.domain.board.entity.QBoard.board;
import static com.unit.daybook.domain.board.entity.QHashtag.hashtag;


@Repository
@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepositoryCustom {
        private final JPAQueryFactory queryFactory;

    public List<Board> test(Long boardId) {

//         return queryFactory
//                 .selectFrom()
//                .join(hashtag.board, board).fetchJoin()
//                .where(
//                        board.boardId.eq(1L)
//                )
//                .fetch();
            return null;

    }

        public List<String> findAllByBoardId(Long boardId) {
            return queryFactory
                    .select(hashtag.content)
                    .from(hashtag)
                    .where(
                        board.boardId.eq(boardId)
                ).fetch();
        }

    public long deleteByBoardId(Long boardId) {
        return queryFactory
                .delete(hashtag)
                .where(hashtag.board.boardId.eq(boardId))
                .execute();
    }
}

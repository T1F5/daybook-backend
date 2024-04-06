package com.unit.daybook.domain.board.repository;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>, HashtagRepositoryCustom {
}

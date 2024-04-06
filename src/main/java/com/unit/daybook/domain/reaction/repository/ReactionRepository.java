package com.unit.daybook.domain.reaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.reaction.entity.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>, ReactionRepositoryCustom {
}

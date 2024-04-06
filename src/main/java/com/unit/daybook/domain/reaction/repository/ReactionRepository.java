package com.unit.daybook.domain.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unit.daybook.domain.reaction.entity.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>, ReactionRepositoryCustom {
}

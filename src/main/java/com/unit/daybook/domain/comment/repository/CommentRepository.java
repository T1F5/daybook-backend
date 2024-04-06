package com.unit.daybook.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unit.daybook.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}

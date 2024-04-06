package com.unit.daybook.domain.comment.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;


}

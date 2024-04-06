package com.unit.daybook.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unit.daybook.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	Optional<Member> findByNicknameAndOauthInfo_OauthId(String nickname, String oauthId);
}

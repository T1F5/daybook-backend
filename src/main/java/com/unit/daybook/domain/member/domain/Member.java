package com.unit.daybook.domain.member.domain;

import java.time.LocalDateTime;

import com.unit.daybook.domain.common.model.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String nickname;

	@Embedded
	private OauthInfo oauthInfo;

	@Enumerated(EnumType.STRING)
	private MemberStatus status;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	private LocalDateTime lastLoginAt;


	@Builder(access = AccessLevel.PRIVATE)
	private Member(
		String nickname,
		OauthInfo oauthInfo,
		MemberStatus status,
		MemberRole role,
		LocalDateTime lastLoginAt) {
		this.nickname = nickname;
		this.oauthInfo = oauthInfo;
		this.status = status;
		this.role = role;
		this.lastLoginAt = lastLoginAt;
	}

	public static Member createNormalMember(OauthInfo oauthInfo, String nickname) {
		return Member.builder()
			.nickname(nickname)
			.oauthInfo(oauthInfo)
			.status(MemberStatus.NORMAL)
			.role(MemberRole.USER)
			.build();
	}

	public void updateLastLoginAt() {
		this.lastLoginAt = LocalDateTime.now();
	}

	public void updateNickname() {
		this.nickname = nickname;
	}
}

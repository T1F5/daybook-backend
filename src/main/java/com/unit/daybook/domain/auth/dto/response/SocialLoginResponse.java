package com.unit.daybook.domain.auth.dto.response;

import com.unit.daybook.domain.member.domain.Member;

public record SocialLoginResponse(
	Long memberId,
	String accessToken,
	String refreshToken) {

	public static SocialLoginResponse of(
		Member member, TokenPairResponse tokenPairResponse) {
		return new SocialLoginResponse(
			member.getId(),
			tokenPairResponse.accessToken(),
			tokenPairResponse.refreshToken());
	}
}

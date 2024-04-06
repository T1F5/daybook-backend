package com.unit.daybook.domain.auth.dto.response;

public record TokenPairResponse(
	String accessToken,
	String refreshToken
) {

	public static TokenPairResponse from(String accessToken, String refreshToken) {
		return new TokenPairResponse(accessToken, refreshToken);
	}
}
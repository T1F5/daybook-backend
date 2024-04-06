package com.unit.daybook.domain.auth.dto.response;

public record AuthTokenResponse(
	String accessToken,
	String refreshToken,
	String grantType,
	Long expiresIn
) {
	public static AuthTokenResponse of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
		return new AuthTokenResponse(accessToken, refreshToken, grantType, expiresIn);
	}
}

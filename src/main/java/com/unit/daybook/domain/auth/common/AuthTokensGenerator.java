package com.unit.daybook.domain.auth.common;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.unit.daybook.domain.auth.dto.response.AuthTokenResponse;
import com.unit.daybook.domain.auth.dto.response.SocialLoginResponse;
import com.unit.daybook.domain.auth.dto.response.TokenPairResponse;
import com.unit.daybook.domain.auth.service.TokenService;
import com.unit.daybook.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
	private static final String BEARER_TYPE = "Bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60
			* 60 * 24;            // 1일
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60
			* 60 * 24 * 7;  // 7일

	private final JwtTokenProvider jwtTokenProvider;
	private final TokenService tokenService;

	public SocialLoginResponse generate(Member member) {

		return SocialLoginResponse.of(
				member,
				TokenPairResponse.from(
					tokenService.generateToken(member, ACCESS_TOKEN_EXPIRE_TIME),
					tokenService.generateRefreshToken(member, REFRESH_TOKEN_EXPIRE_TIME)
				));
	}

	public Long extractUserId(String accessToken) {
		return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
	}
}

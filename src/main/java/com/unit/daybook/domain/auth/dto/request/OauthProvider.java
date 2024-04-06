package com.unit.daybook.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthProvider {
	KAKAO("KAKAO"),
	;
	private final String value;

}

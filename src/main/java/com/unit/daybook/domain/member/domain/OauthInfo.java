package com.unit.daybook.domain.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthInfo {

	private String oauthId;
	private String oauthProvider;
	private String oauthEmail;

	@Builder(access = AccessLevel.PRIVATE)
	private OauthInfo(String oauthId, String oauthProvider, String oauthEmail) {
		this.oauthId = oauthId;
		this.oauthProvider = oauthProvider;
		this.oauthEmail = oauthEmail;
	}

	public static OauthInfo createOauthInfo(
		String oauthId, String oauthProvider, String oauthEmail) {
		return OauthInfo.builder()
			.oauthId(oauthId)
			.oauthProvider(oauthProvider)
			.oauthEmail(oauthEmail)
			.build();
	}
}

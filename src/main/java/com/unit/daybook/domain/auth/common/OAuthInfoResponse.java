package com.unit.daybook.domain.auth.common;

import com.unit.daybook.domain.auth.dto.request.OauthProvider;

public interface OAuthInfoResponse {
	String getSnsId();
	String getEmail();
	String getNickname();
	OauthProvider getOAuthProvider();
	String getProfileImageUrl();
	String getGender();
	String getBirthday();
	String getAgeRange();
}

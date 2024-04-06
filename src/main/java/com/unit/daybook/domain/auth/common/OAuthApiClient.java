package com.unit.daybook.domain.auth.common;

import com.unit.daybook.domain.auth.dto.request.OauthProvider;

public interface OAuthApiClient {
	OauthProvider oAuthProvider();
	String requestAccessToken(OAuthLoginParams params);
	OAuthInfoResponse requestOAuthInfo(String accessToken);
}

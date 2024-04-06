package com.unit.daybook.domain.auth.common;

import org.springframework.util.MultiValueMap;

import com.unit.daybook.domain.auth.dto.request.OauthProvider;

public interface OAuthLoginParams {
	OauthProvider oAuthProvider();
	MultiValueMap<String, String> makeBody();
}

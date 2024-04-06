package com.unit.daybook.domain.auth.kakao;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.unit.daybook.domain.auth.common.OAuthLoginParams;
import com.unit.daybook.domain.auth.dto.request.OauthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
	private String code;

	@Override
	public OauthProvider oAuthProvider() {
		return OauthProvider.KAKAO;
	}

	// Kakao Server에서 response받은 code 값 chaining
	@Override
	public MultiValueMap<String, String> makeBody() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", code);
		return body;
	}
}

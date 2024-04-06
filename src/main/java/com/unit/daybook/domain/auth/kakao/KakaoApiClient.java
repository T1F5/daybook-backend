package com.unit.daybook.domain.auth.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.unit.daybook.domain.auth.common.OAuthApiClient;
import com.unit.daybook.domain.auth.common.OAuthInfoResponse;
import com.unit.daybook.domain.auth.common.OAuthLoginParams;
import com.unit.daybook.domain.auth.dto.request.OauthProvider;
import com.unit.daybook.global.error.exception.CustomException;
import com.unit.daybook.global.error.exception.ErrorCode;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {
	private static final String GRANT_TYPE = "authorization_code";

	@Value("${oauth.kakao.url.auth}")
	private String authUrl;

	@Value("${oauth.kakao.url.api}")
	private String apiUrl;

	@Value("${oauth.kakao.client-id}")
	private String clientId;

	@Value("${oauth.kakao.client-secret")
	private String clientSecret;

	private final RestTemplate restTemplate;

	@Override
	public OauthProvider oAuthProvider() {
		return OauthProvider.KAKAO;
	}

	@Override
	public String requestAccessToken(OAuthLoginParams params) {
		String url = authUrl + "/oauth/token";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = params.makeBody();
		body.add("grant_type", GRANT_TYPE);
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

		KakaoTokens response = restTemplate.exchange(url, HttpMethod.POST, request, KakaoTokens.class).getBody();

		if (response == null) {
			throw new CustomException(ErrorCode.KAKAO_RESPONSE_NOT_FOUND);
		}
		return response.getAccessToken();
	}

	@Override
	public OAuthInfoResponse requestOAuthInfo(String accessToken) {
		String url = apiUrl + "/v2/user/me";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.set("Authorization", "Bearer " + accessToken);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("property_keys", "[\"id\", \"kakao_account.\", \"properties.\", \"has_signed_up.\"]");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

		return restTemplate.exchange(url, HttpMethod.POST, request, KakaoInfoResponse.class).getBody();
	}
}

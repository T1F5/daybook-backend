package com.unit.daybook.domain.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.unit.daybook.domain.auth.common.OAuthApiClient;
import com.unit.daybook.domain.auth.common.OAuthInfoResponse;
import com.unit.daybook.domain.auth.common.OAuthLoginParams;
import com.unit.daybook.domain.auth.dto.request.OauthProvider;

@Component
public class RequestOAuthInfoService {
	private final Map<OauthProvider, OAuthApiClient> clients = new HashMap<>();

	public RequestOAuthInfoService(List<OAuthApiClient> clients) {
		if (clients == null) {
			throw new IllegalArgumentException("Clients cannot be null");
		}

		for (OAuthApiClient client : clients) {
			this.clients.put(client.oAuthProvider(), client);
		}
	}

	public OAuthInfoResponse request(OAuthLoginParams params) {
		if (params.oAuthProvider() == null) {
			throw new IllegalArgumentException("OAuth provider must be specified");
		}

		OAuthApiClient client = clients.get(params.oAuthProvider());
		if (client == null) {
			throw new IllegalArgumentException("No client found for the given OAuth provider");
		}

		String accessToken = client.requestAccessToken(params);
		return client.requestOAuthInfo(accessToken);
	}
}
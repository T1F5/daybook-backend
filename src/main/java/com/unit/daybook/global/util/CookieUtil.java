package com.unit.daybook.global.util;

import static com.unit.daybook.global.common.constants.SecurityConstants.*;

import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieUtil {

	// private final SpringEnvironmentUtil springEnvironmentUtil;

	public HttpHeaders generateTokenCookies(String accessToken, String refreshToken) {

		String sameSite = determineSameSitePolicy();

		ResponseCookie accessTokenCookie =
			ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, accessToken)
				.path("/")
				.secure(true)
				.sameSite(sameSite)
				.httpOnly(true)
				.build();

		ResponseCookie refreshTokenCookie =
			ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
				.path("/")
				.secure(true)
				.sameSite(sameSite)
				.httpOnly(true)
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
		headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

		return headers;
	}

	private String determineSameSitePolicy() {
		// if (springEnvironmentUtil.isProdProfile()) {
			return Cookie.SameSite.STRICT.attributeValue();
		// }
		// return Cookie.SameSite.NONE.attributeValue();
	}

	public HttpHeaders deleteTokenCookies() {

		String sameSite = determineSameSitePolicy();

		ResponseCookie accessTokenCookie =
			ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, "")
				.path("/")
				.maxAge(0)
				.secure(true)
				.sameSite(sameSite)
				.httpOnly(false)
				.build();

		ResponseCookie refreshTokenCookie =
			ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
				.path("/")
				.maxAge(0)
				.secure(true)
				.sameSite(sameSite)
				.httpOnly(false)
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
		headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

		return headers;
	}
}

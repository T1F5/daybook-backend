package com.unit.daybook.domain.auth.service;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.unit.daybook.global.config.security.CustomUserDetails;
import com.unit.daybook.global.error.exception.CustomException;
import com.unit.daybook.global.error.exception.ErrorCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenFilter extends OncePerRequestFilter {

	private final SecurityService securityService;
	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			final String token = TokenService.parseTokenByRequest(request);
			if (token != null) {
				CustomUserDetails userDetails = tokenService.getUserDetailsByToken(token);
				securityService.setAuthentication(userDetails);
			}
		} catch (Exception err) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		filterChain.doFilter(request, response);
	}

}

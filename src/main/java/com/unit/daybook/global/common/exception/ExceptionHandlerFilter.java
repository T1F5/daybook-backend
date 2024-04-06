package com.unit.daybook.global.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unit.daybook.global.error.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Unauthorized e) {
			log.error("Not Authorized.", e);
			ErrorResponse errorResponse = ErrorResponse.of(e.getClass().getName(), e.getMessage());
			setResponseHeaders(response);
			writeErrorResponse(response, HttpStatus.UNAUTHORIZED, errorResponse);
		} catch (Exception e) {
			log.error("Internal Server Error.", e);
			ErrorResponse errorResponse = ErrorResponse.of(e.getClass().getName(), e.getMessage());
			setResponseHeaders(response);
			writeErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
		}
	}

	private void setResponseHeaders(HttpServletResponse response) {
		response.setHeader("content-type", "application/json;charset=UTF-8");
		response.setHeader("cache-control", "no-cache, no-store, max-age=0, must-revalidate");
		response.setHeader("expires", "0");
		response.setHeader("pragma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
	}

	private void writeErrorResponse(HttpServletResponse response, HttpStatus httpStatus, ErrorResponse errorResponse) throws IOException {
		response.setStatus(httpStatus.value());
		try (PrintWriter writer = response.getWriter()) {
			String json = objectMapper.writeValueAsString(errorResponse);
			writer.write(json);
			writer.flush();
		}
	}
}
package com.unit.daybook.global.common.response;

import java.time.LocalDateTime;

import com.unit.daybook.global.error.ErrorResponse;

public record GlobalResponse(int status, Object data, LocalDateTime timestamp) {
	public static GlobalResponse success(int status, Object data) {
		return new GlobalResponse(status, data, LocalDateTime.now());
	}

	public static GlobalResponse fail(int status, ErrorResponse errorResponse) {
		return new GlobalResponse(status, errorResponse, LocalDateTime.now());
	}
}

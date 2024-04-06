package com.unit.daybook.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "Sample Error Message"),

	// Common
	METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "요청 한 값 타입이 잘못되어 binding에 실패하였습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP method 입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),

	// Auth
	KAKAO_RESPONSE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "카카오에 대한 응답값이 존재하지 않습니다."),
	CLAIMS_IS_NULL(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.")
	;
	private final HttpStatus status;
	private final String message;
}

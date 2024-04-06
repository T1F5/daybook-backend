package com.unit.daybook.global.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlConstants {

	PROD_SERVER_URL("https://daybook.shop"),
	LOCAL_SERVER_URL("http://localhost:8080"),

	PROD_DOMAIN_URL("https://www.daybook.co.kr"),
	LOCAL_DOMAIN_URL("http://localhost:3000"),

	;
	private final String value;

}

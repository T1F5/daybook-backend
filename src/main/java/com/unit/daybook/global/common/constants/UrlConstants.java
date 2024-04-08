package com.unit.daybook.global.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlConstants {

	PROD_SERVER_URL("https://daybook.shop"),
	LOCAL_SERVER_URL("http://localhost:8080"),

	PROD_DOMAIN_URL("https://daybook.site"),
	LOCAL_DOMAIN_URL("http://localhost:5173"),

	;
	private final String value;

}

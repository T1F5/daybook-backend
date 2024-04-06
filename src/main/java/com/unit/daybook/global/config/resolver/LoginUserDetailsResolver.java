package com.unit.daybook.global.config.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.unit.daybook.domain.common.annotation.LoginUsers;
import com.unit.daybook.global.config.security.SecurityService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginUserDetailsResolver implements HandlerMethodArgumentResolver {

	private final SecurityService securityService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUsers.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
	                              ModelAndViewContainer mavContainer,
	                              NativeWebRequest webRequest,
	                              WebDataBinderFactory binderFactory) {
		return securityService.getAuthentication();
	}
}
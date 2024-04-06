package com.unit.daybook.global.config.security;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.security.config.Customizer.*;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.unit.daybook.domain.auth.service.TokenFilter;
import com.unit.daybook.global.common.constants.UrlConstants;
import com.unit.daybook.global.common.exception.ExceptionHandlerFilter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

	private final TokenFilter tokenFilter;
	private final ExceptionHandlerFilter exceptionHandlerFilter;

	private static final String[] permitAllUrl = {
		"/",
		"/auth/**",
		"/index.html",
		"/favicon.ico",
		"/robots.txt",
		"/daybook-actuator/**",
		"/assets/**", // static 경로 추가
		"/public/**",
		"/public/fonts/**",
		"/fonts/**",
		"/css/**",
		"/images/**",
		"/js/**",
		"/enums/**",
		"/join/verification-url",
		"/view/users/change-password",
	};

	private void defaultFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.cors(withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	}

	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations()))
			.requestMatchers(
				permitAllUrl
			);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// configuration.addAllowedOriginPattern(UrlConstants.PROD_DOMAIN_URL.getValue());
		// configuration.addAllowedOriginPattern(UrlConstants.LOCAL_DOMAIN_URL.getValue());
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader(SET_COOKIE);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, e) -> {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write("ACCESS DENIED");
			response.getWriter().flush();
			response.getWriter().close();
		};
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, e) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write("UNAUTHORIZED");
			response.getWriter().flush();
			response.getWriter().close();
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		defaultFilterChain(httpSecurity);

		httpSecurity
			.authorizeHttpRequests(req -> req
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers(permitAllUrl).permitAll()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.anyRequest().permitAll()
			)
			.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(exceptionHandlerFilter, TokenFilter.class);
		return httpSecurity.build();
	}
}

package com.unit.daybook.domain.auth.service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.global.config.security.CustomUserDetails;
import com.unit.daybook.global.error.exception.CustomException;
import com.unit.daybook.global.error.exception.ErrorCode;
import com.unit.daybook.global.util.JwtUtil;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

	private static Long DEFAULT_EXPIRATION_MINUTES;
	private final JwtUtil jwtUtil;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${util.jwt.defaultExpirationMinutes}")
	public void setDefaultExpirationMinutes(Long defaultExpirationMinutes) {
		TokenService.DEFAULT_EXPIRATION_MINUTES = defaultExpirationMinutes;
	}

	private static SecretKey createSecretKey(String key) {
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
	}

	public static String parseTokenByRequest(HttpServletRequest request) {
		final String authHeader = request.getHeader("authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	public static String parseReFreshTokenByRequest(HttpServletRequest request) {
		return request.getHeader("refresh-token");
	}

	public Map<String, Object> generateDefaultClaims(Member member, Long plusExpMinutes) {
		LocalDateTime now = LocalDateTime.now();
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", "access-token");
		claims.put("iat", Timestamp.valueOf(now));
		claims.put("exp", Timestamp.valueOf(now.plusMinutes(plusExpMinutes)).getTime() / 1000);
		claims.put("memberId", member.getId());
		claims.put("nickname", member.getNickname());
		claims.put("email", member.getOauthInfo().getOauthEmail() != null ? member.getOauthInfo().getOauthEmail() : null);
		claims.put("snsId", member.getOauthInfo().getOauthId());
		claims.put("createdAt",
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

		return claims;
	}

	public Map<String, Object> generateReFreshClaims(Member member, Long plusExpMinutes) {
		LocalDateTime now = LocalDateTime.now();
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", "refresh-token");
		claims.put("memberId", member.getId());
		claims.put("snsId", member.getOauthInfo().getOauthId());
		claims.put("email", member.getOauthInfo().getOauthEmail());
		claims.put("iat", Timestamp.valueOf(now));
		claims.put("exp", Timestamp.valueOf(now.plusMinutes(plusExpMinutes)).getTime() / 1000);
		claims.put("createdAt",
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

		return claims;
	}

	// 자동 로그인 사용 시 아래 메서드 사용
	public String generateToken(Member user, Boolean remember) {
		Long expMin = remember ? DEFAULT_EXPIRATION_MINUTES * 24 * 7 : DEFAULT_EXPIRATION_MINUTES;
		Map<String, Object> claims = generateDefaultClaims(user, expMin);

		return jwtUtil.generateToken(claims, createSecretKey(secretKey));
	}

	public String generateRefreshToken(Member user, Boolean remember) {
		Long expMin = remember ? DEFAULT_EXPIRATION_MINUTES * 24 * 30 : DEFAULT_EXPIRATION_MINUTES * 4;
		Map<String, Object> claims = generateReFreshClaims(user, expMin);

		return jwtUtil.generateRefreshToken(claims, createSecretKey(secretKey));
	}

	public String generateToken(Member member, Long plusExpMinutes) {
		Map<String, Object> claims = generateDefaultClaims(member, plusExpMinutes);

		return jwtUtil.generateToken(claims, createSecretKey(secretKey));
	}

	public String generateRefreshToken(Member member, Long plusExpMinutes) {
		Map<String, Object> claims = generateReFreshClaims(member, plusExpMinutes);
		String refreshToken = jwtUtil.generateRefreshToken(claims, createSecretKey(secretKey));

		// refreshToken을 세션에 저장
		HttpServletRequest currentRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = currentRequest.getSession();
		session.setAttribute("refreshToken", refreshToken);

		return refreshToken;
	}


	public CustomUserDetails getUserDetailsByToken(String token) {
		Map<String, Object> claims = jwtUtil.getClaims(token);
		if (claims == null) {
			throw new CustomException(ErrorCode.CLAIMS_IS_NULL);
		}

		// token정보 이외에 추가로 정보가 필요해서 DB의 데이터를 조회해서 userDetails정보를 만드려면 UserDetailsService 인터페이스를 캐시서비스 또는 유저서비스에서 구현해서 userDetails를 리턴하게 하면 된다.
		// 꼭 UserDetailsService 인터페이스를 구현하지 않아도 되고 DB를 조회해서 userDetails에 데이터를 넣어서 리턴해주게 만들면된다.
		Long userId = Long.parseLong(claims.get("memberId").toString());
		String userSnsId = claims.get("snsId") != null ? claims.get("snsId").toString() : null;
		String userEmail = claims.get("email") != null ? claims.get("email").toString() : null;
		String userNickname = claims.get("nickname").toString();

		return new CustomUserDetails(userId, userSnsId, userEmail, userNickname);
	}

}

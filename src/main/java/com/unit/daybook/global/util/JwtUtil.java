package com.unit.daybook.global.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${util.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${util.jwt.refreshKey}")
    private String jwtReFreshKey;
    private byte[] secretBytes;
    private byte[] refreshBytes;

    /**
     * JWT 생성
     *
     * @param claims
     * @return
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, secretBytes)
            .compact();
    }

    public String generateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, refreshBytes)
            .compact();
    }

    public String generateToken(Map<String, Object> header, Map<String, Object> claims, Key key) {
        return Jwts.builder()
            .setHeader(header)
            .setClaims(claims)
            .signWith(SignatureAlgorithm.valueOf(header.get(JwsHeader.ALGORITHM).toString()), key)
            .compact();
    }

    /**
     * JWT 검증 및 데이터 가져오기
     *
     * @param jwt
     * @return
     */
    public Map<String, Object> getClaims(String jwt) {
        try {
            if (jwt == null || jwt.isEmpty()) {
                return null;
            }
            Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(jwt)
                .getBody();
            return claims;
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getClaimsForReFresh(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return null;
        }
        return Jwts.parser()
            .setSigningKey(jwtReFreshKey)
            .parseClaimsJws(jwt)
            .getBody();
    }
}

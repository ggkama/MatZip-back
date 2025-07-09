package com.kh.matzip.member.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secretKey;
	
	private SecretKey key;
	
	private final long accessTokenDeadLine = 1000 * 60 * 60 * 2;   // accessToken 유효시간 : 2시간
	private final long refreshTokenDeadLine = 1000 * 60 * 60 * 24 * 7;  // refreshToken 유효시간 : 7일
	
	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	/* AccessToken 생성 */
	public String createAccessToken(String userId, String userRole) {
		return Jwts.builder()
				.subject(userId)
				.claim("userRole", userRole)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + accessTokenDeadLine))
				.signWith(key)
				.compact();
				
	}
	
	/* RefreshToken 생성 */
	public String createRefreshToken() {
		return Jwts.builder()
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + refreshTokenDeadLine))
				.signWith(key)
				.compact();
	}

	/* token에서 ownerNo 가져오기 */
	
	public Long getUserNoFromToken(String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		
		String subject = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject(); // → userNo 들어있음

		return Long.valueOf(subject);
	}
	
	public String extractUserId(String token) {
	    return parseJwt(token).get("userId", String.class);
	}

	public String extractUserRole(String token) {
	    return parseJwt(token).get("userRole", String.class);
	}

	public String extractUserNo(String token) {
	    return parseJwt(token).getSubject();
	}

	public boolean isTokenValid(String token) {
	    try {
	    	parseJwt(token);
	        return true;
	    } catch (JwtException e) {
	        log.error("유효하지 않은 토큰입니다: {}", e.getMessage());
	        return false;
	    }
	}

	public Claims parseJwt(String token) {
	    return Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}
	
	
}

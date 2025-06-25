package com.kh.matzip.member.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	
	// signWith에 사용되는 key 초기화
	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	/* AccessToken 생성 */
	public String createAccessToken(Long userNo, String userId, String userRole) {
		return Jwts.builder()
				.subject(userNo.toString())
				.claim("userId", userId)
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
	
	
	
	
}

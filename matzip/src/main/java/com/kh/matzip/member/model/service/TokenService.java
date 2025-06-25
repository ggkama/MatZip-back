package com.kh.matzip.member.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.member.model.vo.RefreshToken;

@Service
public interface TokenService {

	/* 토큰 생성 */
	Map<String, String> generateToken(Long userNo, String userId, String userRole);
	
	/* 리프레시 토큰 저장 */
	void saveToken(RefreshToken token);
	
	/* 리프레시 토큰 -> 토큰 정보 조회 */
	RefreshToken findByRefreshToken(String token);	
		
}

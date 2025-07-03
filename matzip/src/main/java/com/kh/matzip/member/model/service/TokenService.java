package com.kh.matzip.member.model.service;

import java.util.Date;

import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.vo.RefreshToken;

public interface TokenService {

	/* 토큰 생성 */
	LoginDTO generateToken(LoginDTO loginUser);
	
	/* 리프레시 토큰 저장 */
	void saveToken(RefreshToken token);
	
	/* 리프레시 토큰 -> 토큰 정보 조회 */
	RefreshToken findByRefreshToken(String token);

	/* 토큰 삭제 */
	void deleteToken(Long userNo);
	
	/* 유효 시간이 지난 토큰 삭제 */
	void deleteRefreshToken(Date expiredDate);
		
}
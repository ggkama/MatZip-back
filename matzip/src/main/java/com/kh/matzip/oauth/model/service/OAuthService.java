package com.kh.matzip.oauth.model.service;

import java.util.Map;

import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.dto.MemberDTO;

public interface OAuthService {
	
	/* code(인가코드) -> accessToken */
	String getAccessToken(String code);
	
	/* 카카오 로그인 사용자 정보 가져오기 */
	Map<String, Object> getKakaoUserData(String code);
	
	/* 기존 회원 존재 확인 */
	boolean findByProviderId(String provider, String providerId);
	
	/* 기존 회원 조회 후 새 사용자 저장 */
	MemberDTO createMember(String email, String nickname, String kakaoId, String userName, String userPhone);
	
	/* 새 사용자 저장 */
	MemberDTO registerOAuthUser(String email, String nickname, String kakaoId, String userName, String userPhone);
	
	/* 카카오 회원가입(+이름, +번호) */
	LoginDTO kakaoSignup(String accessToken, String userName, String userPhone);
	
	/* 카카오 로그인 */
	LoginDTO kakaoLogin(String code);
}

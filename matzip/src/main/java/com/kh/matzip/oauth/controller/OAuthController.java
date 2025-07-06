package com.kh.matzip.oauth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.EntityNotFoundException;
import com.kh.matzip.global.error.exceptions.OAuthUserNotFoundException;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.oauth.model.service.OAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthService oauthService;
	
	@PostMapping("/login/kakao")
	public ResponseEntity<ApiResponse<?>> kakaoLogin(@RequestBody Map<String, String> request){
		String code = request.get("code");
	    log.info("카카오 인가 코드 : {}", code);

	    try {
	        LoginDTO loginDTO = oauthService.kakaoLogin(code);
	        
	        return ResponseEntity.ok(ApiResponse.success(ResponseCode.LOGIN_SUCCESS, loginDTO, "카카오 로그인에 성공했습니다."));
	    } catch (OAuthUserNotFoundException e) {
	        Map<String, Object> kakaoUser = e.getKakaoUser();
	        kakaoUser.put("accessToken", e.getAccessToken());
	        
	        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OAUTH_SIGNUP, kakaoUser, "카카오 계정이 등록되어 있지 않습니다."));
	    }
	}
	
	
	@PostMapping("/signup/kakao")
	public ResponseEntity<ApiResponse<LoginDTO>> kakaoSignup(@RequestBody Map<String, String> request){
		
		String accessToken = request.get("accessToken");
		String userName = request.get("userName");
		String userPhone = request.get("userPhone");
		
		LoginDTO loginDTO = oauthService.kakaoSignup(accessToken, userName, userPhone);
	    return ResponseEntity.ok(ApiResponse.success(ResponseCode.OAUTH_SIGNUP, loginDTO, "카카오 회원가입 성공"));
	}
	
}

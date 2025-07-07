package com.kh.matzip.oauth.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.OAuthUserNotFoundException;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.oauth.model.service.OAuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthService oauthService;
	
	@GetMapping("/login/kakao")
	public void kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
	    try {
	        LoginDTO loginDTO = oauthService.kakaoLogin(code);

	        String loginUrl = UriComponentsBuilder
	                .fromUriString("http://localhost:5173/login/result")
	                .queryParam("accessToken", loginDTO.getAccessToken())
	                .queryParam("refreshToken", loginDTO.getRefreshToken())
	                .queryParam("userNo", loginDTO.getUserNo())
	                .queryParam("userRole", loginDTO.getUserRole())
	                .build()
	                .toUriString();

	        response.sendRedirect(loginUrl);

	    } catch (OAuthUserNotFoundException e) {
	        Map<String, Object> kakaoUser = e.getKakaoUser();
	        String signupUrl = UriComponentsBuilder
	                .fromUriString("http://localhost:5173/signup/kakao")
	                .queryParam("userId", kakaoUser.get("userId"))
	                .queryParam("userNickname", kakaoUser.get("userNickname"))
	                .queryParam("accessToken", e.getAccessToken())
	                .build()
	                .toUriString();

	        response.sendRedirect(signupUrl);
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

package com.kh.matzip.oauth.util;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient {
	
	@Value("${kakao.client-id}")
	private String clientId;
	
	@Value("${kakao.redirect-uri}")
	private String redirectUri;
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper;
	
	public String getKakaoAccessToken(String code) {
		log.info("카카오 인가 코드 : {}", code);
		
		String tokenUri = "https://kauth.kakao.com/oauth/token";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		log.info("redirec_uri : {}", redirectUri);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUri, HttpMethod.POST, request, String.class
            );
            
            log.info("access token 요청 응답: {}", response.getBody());

            // JSON 파싱 (access_token 추출)
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
            return (String) responseMap.get("access_token");

        } catch (Exception e) {
            log.error("카카오 access token 요청 실패", e);
            throw new RuntimeException("카카오 access token 요청 실패", e);
        }
		
	}
	
	 public Map<String, Object> getUserInfo(String accessToken) {
		 	log.info("사용자 정보 요청할 accessToken: {}", accessToken);
	        String userInfoUri = "https://kapi.kakao.com/v2/user/me";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(accessToken);

	        HttpEntity<Void> request = new HttpEntity<>(headers);

	        try {
	            ResponseEntity<String> response = restTemplate.exchange(
	                    userInfoUri, HttpMethod.GET, request, String.class
	            );
	            log.info("사용자 정보 응답: {}", response.getBody());

	            Map<String, Object> userInfo = objectMapper.readValue(response.getBody(), Map.class);
	            return userInfo;

	        } catch (Exception e) {
	            log.error("카카오 사용자 정보 요청 실패", e);
	            throw new RuntimeException("카카오 사용자 정보 요청 실패", e);
	        }
	    }
	
	

}

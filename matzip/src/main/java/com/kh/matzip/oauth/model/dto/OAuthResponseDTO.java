package com.kh.matzip.oauth.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OAuthResponseDTO {
	
	private Long oauthNo;        // TB_OAUTH_USER.OAUTH_NO (PK)
    private Long userNo;         // 연결된 TB_USER.USER_NO (FK)
    
    private String userId;       // 사용자 아이디 (보통 이메일)
    private String provider;     // 제공자 (kakao, google 등)
    private String providerId;   // 제공자에서 제공한 고유 ID
    private String email;        // 사용자의 이메일
    private String userNickname; // 사용자 닉네임 (카카오 닉네임 등)
    
    private Date enrollDate;     // 등록일
	
}

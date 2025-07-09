package com.kh.matzip.oauth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KakaoSignupDTO {
	
	private String kakaoId;
    private String email;
    private String nickname;
    private String userName;
    private String userPhone;

}

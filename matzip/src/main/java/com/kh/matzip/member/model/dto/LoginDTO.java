package com.kh.matzip.member.model.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginDTO {

	private Long userNo;
	private String userId;
    private String userName;
    private String userNickName;
    private String userRole;
    private String isDeleted;
    private Date modifiedDate;
    
    private String accessToken;
    private String refreshToken;
    
}

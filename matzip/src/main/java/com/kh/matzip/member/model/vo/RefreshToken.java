package com.kh.matzip.member.model.vo;


import java.util.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {

	private Long tokenNo;
	private Long userNo;
	private String refreshToken;
	private Date expiredDate;
	
}

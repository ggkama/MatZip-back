package com.kh.matzip.common.model.service.verification;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationData {

	private final String code;
	private final LocalDateTime createTime;
	
	
}


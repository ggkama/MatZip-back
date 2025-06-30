package com.kh.matzip.common.model.service.verification;

import java.time.LocalDateTime;

public interface VerificationService {
	
	/* 인증코드 생성 */
	String createVerificationCode(String email, LocalDateTime createTime);
	
	/* 인증 코드 확인 */
	boolean verifyCode(String email, String code);
	
	/* 이메일 인증 확인 */
	boolean isEmailVerified(String email);

}

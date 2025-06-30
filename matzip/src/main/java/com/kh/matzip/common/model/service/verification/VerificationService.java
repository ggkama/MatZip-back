package com.kh.matzip.common.model.service.verification;

public interface VerificationService {
	
	/* 인증코드 생성 */
	String createVerificationCode(String email);
	
	/* 인증 코드 확인 */
	boolean verifyCode(String email, String code);
	
	/* 이메일 인증 확인 */
	boolean isEmailVerified(String email);

}

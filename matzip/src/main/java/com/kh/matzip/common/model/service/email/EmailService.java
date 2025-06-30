package com.kh.matzip.common.model.service.email;

public interface EmailService {

	/* 이메일 발송 */
	void sendEmail(String toEmail, String title, String text);

	/* 회원가입_이메일 발송 */
	void sendSignUpByEmail(String toEmail, String title, String verifiedCode);
	
}

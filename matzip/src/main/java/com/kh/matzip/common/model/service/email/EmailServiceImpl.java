package com.kh.matzip.common.model.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.AuthenticateFailException;
import com.kh.matzip.global.error.exceptions.InvalidAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;
	
	@Value("${spring.mail.username}")
	private String senderEmail;
	
	@Override
	public void sendEmail(String toEmail, String title, String text) {
		
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setFrom(senderEmail);
			message.setTo(toEmail);
			message.setSubject(title);
			message.setText(text);
			
			emailSender.send(message);
			
			log.info("이메일 발송 성공 : 수신자 = {}", toEmail);
			
		} catch(Exception e) {
			log.error("이메일 발송 실패 : {}", e.getMessage());
			throw new InvalidAccessException(ResponseCode.MAIL_CODE_SEND_FAIL, "이메일 인증 코드 전송에 실패했습니다.");
		}
	}
	
	@Override
	public void sendSignUpByEmail(String toEmail, String title, String verifiedCode) {
		String subject = "[MatZip] 회원가입 이메일 인증";
	    String content = 
	            "MatZip 회원가입을 환영합니다!\n\n" +
	            "회원가입을 완료하기 위한 이메일 인증 코드입니다:\n\n" +
	            verifiedCode + "\n\n" +
	            "인증 코드는 30분 동안 유효합니다.\n\n" +
	            "감사합니다.\n" +
	            "Team 김이박";
	    
	    sendEmail(toEmail, subject, content);
	}
	
}

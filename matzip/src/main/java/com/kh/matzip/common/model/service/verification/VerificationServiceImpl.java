package com.kh.matzip.common.model.service.verification;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.AuthenticateFailException;
import com.kh.matzip.global.error.exceptions.AuthenticateTimeOutException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VerificationServiceImpl implements VerificationService {
	
	private final int VERIFICATION_CODE_EXPIRY_TIME = 30;

	private final Map<String, VerificationData> verificationCode = new HashMap<>();
	
	
	private String createRandomCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000);
		return String.valueOf(code);
	}
	
	@Override
	public String createVerificationCode(String email) {
		String code = createRandomCode();
		
		VerificationData data = new VerificationData(code, LocalDateTime.now());
		verificationCode.put(email, data);
		
		log.info("인증 코드 생성 : 이메일 = {}, 코드 = {}", email, code);
		return code;
	}
	

	private boolean codeExpired(LocalDateTime createTime) {
		return createTime.plusMinutes(VERIFICATION_CODE_EXPIRY_TIME)
				.isBefore(LocalDateTime.now());
	}
	
	@Override
	public boolean verifyCode(String email, String code) {
		VerificationData data = verificationCode.get(email);
		
		if(data == null) {
			throw new AuthenticateFailException(ResponseCode.MAIL_CODE_NULL, "인증 코드가 존재하지 않음");
		}
		
		if(codeExpired(data.getCreateTime())) {
			throw new AuthenticateTimeOutException(ResponseCode.MAIL_CODE_FAIL, "인증 코드 유효 시간이 만료");
		}
		
		if(!data.getCode().equals(code)) {
			throw new AuthenticateFailException(ResponseCode.MAIL_CODE_FAIL, "이메일 인증 실패");
		}
		
		log.info("인증 코드 일치");
		return true;
	}

	@Override
	public boolean isEmailVerified(String email) {
		
		return true;
	}

}

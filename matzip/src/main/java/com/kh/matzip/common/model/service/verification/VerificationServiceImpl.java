package com.kh.matzip.common.model.service.verification;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.kh.matzip.common.model.dao.EmailMapper;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.AuthenticateFailException;
import com.kh.matzip.global.error.exceptions.AuthenticateTimeOutException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
	
	private final int VERIFICATION_CODE_EXPIRY_TIME = 30;
	private final EmailMapper emailMapper;
	
	private String createRandomCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000);
		return String.valueOf(code);
	}
	
	@Override
	public String createVerificationCode(String email, LocalDateTime createTime) {
		String code = createRandomCode();

        VerificationData emailCode = VerificationData.builder()
        		.email(email)
                .emailCode(code)
                .createTime(createTime)
                .build();

        emailMapper.insertEmailCode(emailCode);

        log.info("인증 코드 생성 : 이메일 = {}, 코드 = {}", email, code);
        return code;
	}
	
	
	private boolean codeExpired(LocalDateTime createTime) {
	    return createTime.plusMinutes(VERIFICATION_CODE_EXPIRY_TIME)
	                     .isBefore(LocalDateTime.now());
	}
	
	@Override
	public boolean verifyCode(String email, String code) {
		VerificationData data = emailMapper.selectByEmail(email);
		log.info("인증 코드 : {}", data);
		
		if(data == null) {
			throw new AuthenticateFailException(ResponseCode.MAIL_CODE_NULL, "인증 코드가 존재하지 않음");
		}
		
		if(codeExpired(data.getCreateTime())) {
			throw new AuthenticateTimeOutException(ResponseCode.MAIL_CODE_FAIL, "인증 코드 유효 시간 만료");
		}
		
		if(!data.getEmailCode().equals(code)) {
			throw new AuthenticateFailException(ResponseCode.MAIL_CODE_FAIL, "이메일 인증 실패");
		}
		
		return true;
	}

	@Override
	public boolean isEmailVerified(String email) {
		
		return true;
	}

}

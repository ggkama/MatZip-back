package com.kh.matzip.common.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.common.model.service.email.EmailService;
import com.kh.matzip.common.model.service.verification.VerificationService;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.AuthenticateFailException;
import com.kh.matzip.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;
	private final VerificationService verificationService;
	
	@PostMapping("/send-code")
	public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request) {
		String email = request.get("userId");
		String code = verificationService.createVerificationCode(email);
		emailService.sendSignUpByEmail(email, "[Matzip] 이메일 인증", code);		
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.MAIL_CODE_SEND_SUCCESS, "인증 코드 전송 성공"));
	}
	
	@PostMapping("/verify-code")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        if (!verificationService.verifyCode(email, code)) {
        	throw new AuthenticateFailException(ResponseCode.MAIL_CODE_FAIL, "이메일 인증 실패");
        }
        
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.MAIL_CODE_SUCCESS, "이메일 인증 성공"));
    }

    @PostMapping("/check-status")
    public ResponseEntity<?> check(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean verified = verificationService.isEmailVerified(email);
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.MAIL_CODE_SEND_SUCCESS, "이메일 인증 완료"));
    }
	
}

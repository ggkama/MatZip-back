package com.kh.matzip.member.model.service;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.matzip.common.model.service.verification.VerificationService;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.AuthenticateFailException;
import com.kh.matzip.global.error.exceptions.DuplicateDataException;
import com.kh.matzip.global.error.exceptions.InvalidAccessException;
import com.kh.matzip.member.model.dao.MemberMapper;
import com.kh.matzip.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    private final TokenService tokenService;
    
    @Override
    public void signUp(MemberDTO member) {
    	if (memberMapper.existByUserId(member.getUserId()) > 0) {
			throw new DuplicateDataException(ResponseCode.DUPLICATED_ID, "이미 사용 중인 아이디입니다.");
        }
    	
    	if (!verificationService.isEmailVerified(member.getUserId())) {
    		throw new AuthenticateFailException(ResponseCode.MAIL_CODE_FAIL, "이메일 인증이 완료되지 않았습니다.");
    	}

        if (memberMapper.existByUserNickName(member.getUserNickName()) > 0) {
            throw new DuplicateDataException(ResponseCode.DUPLICATED_NICKNAME, "이미 사용 중인 닉네임입니다.");
        }

        MemberDTO memberValue = MemberDTO.builder()
                .userId(member.getUserId())
                .userPw(passwordEncoder.encode(member.getUserPw()))
                .userName(member.getUserName())
                .userNickName(member.getUserNickName())
                .userPhone(member.getUserPhone())
                .userRole("USER")
                .build();

        int result = memberMapper.signUp(memberValue);
        log.info("회원가입 결과: {}", result);
    }

    
	@Override
	public Map<String, String> login(MemberDTO memberDTO) {
		MemberDTO findUser = memberMapper.findByUserId(memberDTO.getUserId());
		
		if (findUser == null) {
	        throw new InvalidAccessException(ResponseCode.INVALID_USERDATA, "아이디가 존재하지 않습니다.");
	    }

	    if (!passwordEncoder.matches(memberDTO.getUserPw(), findUser.getUserPw())) {
	        throw new InvalidAccessException(ResponseCode.INVALID_USERDATA, "비밀번호가 일치하지 않습니다.");
	    }

	    return tokenService.generateToken(
	        findUser.getUserNo(),
	        findUser.getUserId(),
	        findUser.getUserRole()
	    );

	}
    
    
}

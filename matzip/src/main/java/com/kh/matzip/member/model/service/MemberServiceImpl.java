package com.kh.matzip.member.model.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.matzip.common.model.service.verification.VerificationService;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.AuthenticateFailException;
import com.kh.matzip.global.error.exceptions.DuplicateDataException;
import com.kh.matzip.global.error.exceptions.InvalidAccessException;
import com.kh.matzip.global.error.exceptions.UserAlreadyDeletedException;
import com.kh.matzip.member.model.dao.MemberMapper;
import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.model.vo.CustomUserDetails;

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
    private final AuthenticationManager authenticationManager;
    
    
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
	public LoginDTO login(MemberDTO memberDTO) {
		MemberDTO findUser = memberMapper.findByUserId(memberDTO.getUserId());
		
		if (findUser == null) {
	        throw new InvalidAccessException(ResponseCode.INVALID_USERDATA, "아이디 또는 비밀번호를 잘못 입력하셨습니다.");
	    }

	    if ("Y".equals(findUser.getIsDeleted())) {
	        throw new UserAlreadyDeletedException(ResponseCode.USER_IS_DELETED, "이미 탈퇴 처리된 회원입니다.");
	    }
		
		Authentication authentication = null;
		
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(memberDTO.getUserId(),
												            memberDTO.getUserPw())
					);
			
		} catch(AuthenticationException e) {
			throw new InvalidAccessException(ResponseCode.INVALID_USERDATA, "아이디 또는 비밀번호를 잘못 입력하셨습니다.");
		}
		
	    CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();

	    log.info("user 정보 : {}", user);
	    
	    LoginDTO loginUser = LoginDTO.builder()
	            .userNo(findUser.getUserNo())
	            .userId(findUser.getUserId())
	            .userRole(findUser.getUserRole())
	            .userName(findUser.getUserName())
	            .userNickName(findUser.getUserNickName())
	            .isDeleted(findUser.getIsDeleted())
	            .modifiedDate(findUser.getModifiedDate())
	            .build();

	    return tokenService.generateToken(loginUser);

	}
    
    
}

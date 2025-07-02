package com.kh.matzip.member.model.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.kh.matzip.member.model.dao.TokenMapper;
import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.vo.RefreshToken;
import com.kh.matzip.member.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final JWTUtil jwtUtil;
	private final TokenMapper tokenMapper;
	
	@Override
	public LoginDTO generateToken(LoginDTO loginUser) {
		
		String accessToken = jwtUtil.createAccessToken(loginUser.getUserId(), loginUser.getUserRole());
		
		// refreshToken이 있는지 확인 후 생성하기 위함
		RefreshToken existToken = tokenMapper.selectByUserNo(loginUser.getUserNo());
		String refreshToken = null;
		
		if(existToken == null) {
			refreshToken = jwtUtil.createRefreshToken();
			
			Date refreshTokenDeadLine = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7);
			
			RefreshToken token = RefreshToken.builder()
					.userNo(loginUser.getUserNo())
	                .refreshToken(refreshToken)
	                .expiredDate(refreshTokenDeadLine)
	                .build();

			tokenMapper.saveToken(token);
			
		} else {
			refreshToken = existToken.getRefreshToken();
		}
        
        loginUser.setAccessToken(accessToken);
        loginUser.setRefreshToken(refreshToken);
        
        return loginUser;
	}

	@Override
	public void saveToken(RefreshToken token) {
		tokenMapper.saveToken(token);
	}

	@Override
	public RefreshToken findByRefreshToken(String token) {
		return tokenMapper.selectByToken(token);
	}

	@Override
	public void deleteToken(Long userNo) {
		tokenMapper.deleteToken(userNo);
	}

	@Override
	public void deleteRefreshToken(Date expiredDate) {
		tokenMapper.deleteRefreshToken(expiredDate);
	}

}

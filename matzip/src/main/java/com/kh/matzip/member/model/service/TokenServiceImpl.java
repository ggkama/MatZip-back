package com.kh.matzip.member.model.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.member.model.dao.TokenMapper;
import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.vo.RefreshToken;
import com.kh.matzip.member.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final JWTUtil jwtUtil;
	private final TokenMapper tokenMapper;
	
	@Override
	public LoginDTO generateToken(LoginDTO loginUser) {
		
		String accessToken = jwtUtil.createAccessToken(
				String.valueOf(loginUser.getUserNo()), loginUser.getUserId(), loginUser.getUserRole());
		String refreshToken = jwtUtil.createRefreshToken();
		
		Date refreshTokenDeadLine = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7);
		
		RefreshToken token = RefreshToken.builder()
				.userNo(loginUser.getUserNo())
                .refreshToken(refreshToken)
                .expiredDate(refreshTokenDeadLine)
                .build();

        tokenMapper.saveToken(token);
        
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

}

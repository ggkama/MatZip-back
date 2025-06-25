package com.kh.matzip.member.model.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.member.model.dao.TokenMapper;
import com.kh.matzip.member.model.vo.RefreshToken;
import com.kh.matzip.member.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final JWTUtil jwtUtil;
	private final TokenMapper tokenMapper;
	
	@Override
	public Map<String, String> generateToken(Long userNo, String userId, String userRole) {
		
		String accessToken = jwtUtil.createAccessToken(userNo, userId, userRole);
		String refreshToken = jwtUtil.createRefreshToken();
		
		Date refreshTokenDeadLine = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7);
		
		RefreshToken token = RefreshToken.builder()
				.userNo(userNo)
                .refreshToken(refreshToken)
                .expiredDate(refreshTokenDeadLine)
                .build();

        tokenMapper.saveToken(token);
        
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
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

package com.kh.matzip.oauth.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.DuplicateDataException;
import com.kh.matzip.global.error.exceptions.EntityNotFoundException;
import com.kh.matzip.global.error.exceptions.OAuthUserNotFoundException;
import com.kh.matzip.member.model.dao.MemberMapper;
import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.model.service.TokenService;
import com.kh.matzip.oauth.model.dao.OAuthMapper;
import com.kh.matzip.oauth.model.dto.OAuthResponseDTO;
import com.kh.matzip.oauth.util.KakaoApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final PasswordEncoder passwordEncoder;
    // 소셜로그인은 비밀번호를 못 받아오니까 내가 임의로 설정함
    private final String OAUTH_SOCIAL_PASSWORD = "OAUTH_SOCIAL_PASSWORD";
	
	private final KakaoApiClient kakaoApiClient;
	private final OAuthMapper oauthMapper;
	private final MemberMapper memberMapper;
	private final TokenService tokenService;
	
	
	@Override
    public LoginDTO kakaoSignup(String accessToken, String userName, String userPhone) {
        Map<String, Object> kakaoUser = kakaoApiClient.getUserInfo(accessToken);
        
        String kakaoId = String.valueOf(kakaoUser.get("id"));
        Map<String, Object> account = (Map<String, Object>) kakaoUser.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        String email = (String) kakaoUser.getOrDefault("email", "kakao@" + kakaoId);
        String nickname = (String) kakaoUser.getOrDefault("nickname", "카카오사용자");

        log.info("카카오 회원가입 요청: email={}, nickname={}, kakaoId={}", email, nickname, kakaoId);

        if (oauthMapper.findByProviderId("kakao", kakaoId) != null) {
            throw new DuplicateDataException(ResponseCode.DUPLICATED_ID, "이미 가입된 카카오 계정입니다.");
        }

        MemberDTO newUser = registerOAuthUser(email, nickname, kakaoId, userName, userPhone);

        return tokenService.generateToken(memberToLogin(newUser));
    }

	
	 @Override
	 public LoginDTO kakaoLogin(String code) {
		 String accessToken = kakaoApiClient.getKakaoAccessToken(code);
		 
		 Map<String, Object> kakaoUser = kakaoApiClient.getUserInfo(accessToken);
	     
		 String kakaoId = String.valueOf(kakaoUser.get("id"));

	     OAuthResponseDTO existingOAuth = oauthMapper.findByProviderId("kakao", kakaoId);

        if (existingOAuth == null) {
        	throw new OAuthUserNotFoundException(accessToken, kakaoUser);
	    }

        MemberDTO user = memberMapper.findByUserId(existingOAuth.getUserId());

        if (user == null) {
            throw new EntityNotFoundException(ResponseCode.INVALID_USERDATA, "연결된 회원 정보가 없습니다.");
        }

        return tokenService.generateToken(memberToLogin(user));
    }

	 
	@Override
	public boolean findByProviderId(String provider, String providerId) {
		OAuthResponseDTO result = oauthMapper.findByProviderId(provider, providerId);
		
		if(result != null) {
			throw new DuplicateDataException(ResponseCode.DUPLICATED_ID, "이미 존재하는 계정입니다.");
		}

		return true;
	}
	
	

	@Override
	public Map<String, Object> getKakaoUserData(String accessToken) {
	    Map<String, Object> userInfo = kakaoApiClient.getUserInfo(accessToken);

	    Map<String, Object> account = (Map<String, Object>) userInfo.get("kakao_account");
	    Map<String, Object> profile = (Map<String, Object>) account.get("profile");

	    Map<String, Object> result = new HashMap<>();
	    result.put("id", userInfo.get("id"));
	    result.put("email", account.get("email"));
	    result.put("nickname", profile.get("nickname"));

	    return result;
	}

	@Override
	public MemberDTO createMember(String email, String nickname, String kakaoId, String userName, String userPhone) {
		OAuthResponseDTO existingOAuth = oauthMapper.findByProviderId("kakaoId", kakaoId);
		
		if(existingOAuth != null) {
			MemberDTO user = memberMapper.findByUserId(existingOAuth.getUserId());
			
			if(user == null) {
				throw new EntityNotFoundException(ResponseCode.INVALID_USERDATA, "연결된 회원 정보가 없습니다.");
			}
			return user;
		}
		return registerOAuthUser(email, nickname, kakaoId, userName, userPhone);
	}


	@Override
	public MemberDTO registerOAuthUser(String email, String nickname, String kakaoId, String userName, String userPhone) {
		
		MemberDTO user = new MemberDTO();
					user.setUserId(email);
					user.setUserPw(passwordEncoder.encode(OAUTH_SOCIAL_PASSWORD));
					user.setUserName(userName);
					user.setUserNickName(nickname);
					user.setUserPhone(userPhone);
					user.setUserRole("ROLE_USER");
					user.setIsDeleted("N");
					
		memberMapper.signUp(user);
		
		
		OAuthResponseDTO oauth = new OAuthResponseDTO();
							oauth.setUserNo(user.getUserNo());
							oauth.setUserId(email);
							oauth.setProvider("kakao");
							oauth.setProviderId(kakaoId);
							oauth.setEmail(email);
							oauth.setUserNickname(nickname);
		
		log.info("userNo : {}", user.getUserNo());
		
		oauthMapper.insertOAuthUser(oauth);
		
		return user;
	}

	
	// MemberDTO를 LoginDTO로 변환
	private LoginDTO memberToLogin(MemberDTO user) {
		
		return LoginDTO.builder()
	                .userNo(user.getUserNo())
	                .userId(user.getUserId())
	                .userName(user.getUserName())
	                .userNickName(user.getUserNickName())
	                .userRole(user.getUserRole())
	                .isDeleted(user.getIsDeleted())
	                .modifiedDate(user.getModifiedDate())
	                .build();
		}


	@Override
	public String getAccessToken(String code) {

		return kakaoApiClient.getKakaoAccessToken(code);
	}
		
	
}

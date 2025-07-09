package com.kh.matzip.oauth.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.matzip.oauth.model.dto.OAuthResponseDTO;

@Mapper
public interface OAuthMapper {

	/* 카카오 user_id로 기존 회원 조회*/
	OAuthResponseDTO findByProviderId(@Param("provider") String provider, @Param("providerId") String providerId);
	
	/* 소셜 사용자 정보 저장 */
	void insertOAuthUser(OAuthResponseDTO oauth);
	
}

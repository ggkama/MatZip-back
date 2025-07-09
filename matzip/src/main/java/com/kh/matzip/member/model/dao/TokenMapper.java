package com.kh.matzip.member.model.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.member.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	void saveToken(RefreshToken token);
	
	RefreshToken selectByToken(String token);
	
	RefreshToken selectByUserNo(Long userNo);
	
	void deleteToken(Long userNo);
	
	void deleteRefreshToken(Date expiredDate);
}

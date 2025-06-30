package com.kh.matzip.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.member.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	void saveToken(RefreshToken token);
	
	RefreshToken selectByToken(String token);
}

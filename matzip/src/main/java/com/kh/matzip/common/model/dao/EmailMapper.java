package com.kh.matzip.common.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.common.model.service.verification.VerificationData;

@Mapper
public interface EmailMapper {

	int insertEmailCode(VerificationData verificationData);
	
	VerificationData selectByEmail(String email);
	
}

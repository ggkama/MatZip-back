package com.kh.matzip.owner.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.owner.model.dto.RegisterOwnerDTO;

@Mapper
public interface RegisterOwnerMapper {

	/* 이미 사장님 신청을 했는지 확인 */
	int isOwnerRegister(Long userNo);
	
	/* 같은 사업자 등록 번호가 있는지 확인 */
	int isStoreRegister(String businessNo);
	
	/* 신청 등록 */
	int insertRegisterOwner(RegisterOwnerDTO registerOwnerDTO);
}

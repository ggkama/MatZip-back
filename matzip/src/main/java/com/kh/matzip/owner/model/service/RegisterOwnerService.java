package com.kh.matzip.owner.model.service;

import com.kh.matzip.owner.model.dto.RegisterOwnerDTO;

public interface RegisterOwnerService {

	/* 사용자가 사장님 등록 신청 */
	void registerOwner(RegisterOwnerDTO registerOwnerDTO);
	
}

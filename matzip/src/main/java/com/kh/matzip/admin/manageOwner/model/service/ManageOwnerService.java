package com.kh.matzip.admin.manageOwner.model.service;

import java.util.Map;

import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerDetailDTO;

public interface ManageOwnerService {
    
	/* 사장님 권한 변경 신청 사용자 조회 */
	public Map<String, Object> getOwnerListByAdmin(int page, int size);
	
	/* 사장님 권한 신청 상세 조회 */
	public ManageOwnerDetailDTO getOwnerDetailByAdmin(Long registerNo);
	
	/* 권한 부여 (승인) */
	void approveOwner(Long registerNo);
	
	/* 권한 철회 (반려) */
	void rejectOwner(Long registerNo);
	
	
}

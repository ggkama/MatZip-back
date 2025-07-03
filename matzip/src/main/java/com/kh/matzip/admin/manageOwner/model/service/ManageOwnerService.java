package com.kh.matzip.admin.manageOwner.model.service;

import java.util.Map;

public interface ManageOwnerService {
    
	/* 사장님 권한 변경 신청 사용자 조회 */
	public Map<String, Object> getOwnerListByAdmin(int pageNo, int size);
	
}

package com.kh.matzip.admin.manageUser.model.servcie;

import java.util.Map;


public interface ManageUserService {
	
	/* 전체 사용자 목록 조회 */
	public Map<String, Object> getUserListByAdmin(int pageNo, int size);
	
	/* 사장님 등록 신청한 사용자 목록 조회 */
    public Map<String, Object> getOwnerListByAdmin(int pageNo, int size);
}

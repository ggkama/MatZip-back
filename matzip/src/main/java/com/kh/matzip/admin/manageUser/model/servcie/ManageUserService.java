package com.kh.matzip.admin.manageUser.model.servcie;

import java.util.Map;

import com.kh.matzip.admin.manageUser.model.dto.ManageUserDTO;


public interface ManageUserService {
	
	/* 전체 사용자 목록 조회 */
	public Map<String, Object> getUserListByAdmin(int pageNo, int size);
	
	/* 사용자 상세 조회 */
	public ManageUserDTO getUserDetailByAdmin(Long userNo);
	
	/* 사용자 탈퇴 처리 */
	public void unregisterUserByAdmin(Long userNo);

}

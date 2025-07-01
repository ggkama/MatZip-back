package com.kh.matzip.admin.manageUser.model.servcie;

public interface UnregisterService {

	/* 신청자의 권한 확인 */

	
	/* 관리자가 회원 탈퇴 시킴 */
	void updateStatusUser(String userNo);
	
}

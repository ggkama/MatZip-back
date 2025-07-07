package com.kh.matzip.admin.manageStore.model.service;

import java.util.Map;

public interface ManageStoreService {
    
	/* 관리자 - 가게 리스트 조회 */
	Map<String, Object> getStoreListByAdmin(int page, int size);
	
	/* 관리자 - 가게 삭제 */
	void deleteStore(Long storeNo);
	
}

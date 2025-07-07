package com.kh.matzip.admin.manageReview.model.service;

import java.util.Map;

public interface ManageReviewService {

	/* 관리자 - 가게 리스트 조회 */
	Map<String, Object> getReviewListByAdmin(int page, int size);
	
}

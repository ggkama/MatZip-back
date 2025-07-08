package com.kh.matzip.admin.manageReview.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.admin.manageReview.model.dto.ManageReivewListDTO;

@Mapper
public interface ManageReviewMapper {

	/* 관리자_가게 리스트 조회 */
	List<ManageReivewListDTO> selectReviewList(Map<String, Object> param);
	
	/* 총 가게 수 조회 */
	int countAllReview();
	
	
}

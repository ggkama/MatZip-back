package com.kh.matzip.admin.manageOwner.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerListDTO;

@Mapper
public interface ManageOwnerMapper {
	
	/* 사용자 목록 조회 */
	List<ManageOwnerListDTO> selectOwnerList(Map<String, Object> param);
	
}

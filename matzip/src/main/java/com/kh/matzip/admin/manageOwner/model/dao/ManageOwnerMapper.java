package com.kh.matzip.admin.manageOwner.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerDetailDTO;
import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerListDTO;

@Mapper
public interface ManageOwnerMapper {
	
	/* 사용자 목록 조회 */
	List<ManageOwnerListDTO> selectOwnerList(Map<String, Object> param);
	
	/* 사장님 권한 사용자 상세 조회 */
	ManageOwnerDetailDTO selectOwnerDetail(Long registerNo);
	
	/* 총 사장님 신청 수 */
	int countAllStores();
	
	/* 신청 상태 조회 */
	String findStatusByRegisterNo(Long registerNo);
	
	/* TB_OWNER의 상태 업데이트 (승인 처리) */
	int updateStatusOwner(Long registerNo);
	
	/* TB_USER의 권한 업데이트 (OWNER로 변경) */
	int updateUserRoleByRegisterNo(Long registerNo);
	
}

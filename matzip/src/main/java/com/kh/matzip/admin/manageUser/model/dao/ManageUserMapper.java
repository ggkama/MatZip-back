package com.kh.matzip.admin.manageUser.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.admin.manageUser.model.dto.ManageUserDTO;

@Mapper
public interface ManageUserMapper {
	
	/* 사용자 목록 조회 */
	List<ManageUserDTO> selectUserList(Map<String, Object> param);
	
	/* 사장님 등록 신청 사용자 목록 조회 */
	List<ManageUserDTO> selectOwnerList(Map<String, Object> param);
	
	/* 관리자 권한_사용자 탈퇴 처리 */
	int updateStatusUser(Long userNo);
}

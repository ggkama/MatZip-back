package com.kh.matzip.admin.manageUser.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.admin.manageUser.model.dto.ManageUserDTO;

@Mapper
public interface ManageUserMapper {
	
	/* 사용자 목록 조회 */
	List<ManageUserDTO> selectUserList(Map<String, Object> param);
	
	/* 사용자 상세 조회 */
	ManageUserDTO selectUserDetail(Long userNo);
	
	/* 사용자 탈퇴 처리 */
	int updateStatusUser(Long userNo);
	
	/* 총 사용자 수 */
	int countAllUsers();
}

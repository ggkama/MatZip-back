package com.kh.matzip.admin.manageStore.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.admin.manageStore.model.dto.ManageStoreListDTO;

@Mapper
public interface ManageStoreMapper {

	/* 관리자_가게 리스트 조회 */
	List<ManageStoreListDTO> selectStoreList(Map<String, Object> param);
	
	/* 총 가게 수 조회 */
	int countAllStore();
	
	/* 가게 정보 삭제 */
	int deleteStore(Long storeNo);
	
}

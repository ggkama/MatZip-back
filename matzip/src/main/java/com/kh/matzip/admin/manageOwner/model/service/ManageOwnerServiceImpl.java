package com.kh.matzip.admin.manageOwner.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.admin.manageOwner.model.dao.ManageOwnerMapper;
import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerListDTO;
import com.kh.matzip.util.pagenation.PagenationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageOwnerServiceImpl implements ManageOwnerService {

	private final ManageOwnerMapper manageownerMapper;
	private final PagenationService pagenationService;
	
	@Override
	public Map<String, Object> getOwnerListByAdmin(int pageNo, int size) {
		int startIndex = pagenationService.getStartIndex(pageNo, size);
		
		Map<String, Object> param = new HashMap<>();
		param.put("startIndex", startIndex);
		param.put("size", size);
		
		List<ManageOwnerListDTO> ownerList = manageownerMapper.selectOwnerList(param);
		
		Map<String, Object> resultList = new HashMap<>();
		resultList.put("ownerList", ownerList);
		resultList.put("pageNo", pageNo);
		resultList.put("size", size);
		
		return resultList;
	}
    
}

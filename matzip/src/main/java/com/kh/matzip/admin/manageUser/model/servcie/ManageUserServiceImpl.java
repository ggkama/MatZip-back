package com.kh.matzip.admin.manageUser.model.servcie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.admin.manageUser.model.dao.ManageUserMapper;
import com.kh.matzip.admin.manageUser.model.dto.ManageUserDTO;
import com.kh.matzip.util.pagenation.PagenationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageUserServiceImpl implements ManageUserService {
	
	private final ManageUserMapper manageuserMapper;
	private final PagenationService pagenationService;
	
	
	@Override
	public Map<String, Object> getUserListByAdmin(int page, int size) {
		int startIndex = pagenationService.getStartIndex(page, size);
		
		Map<String, Object> param = new HashMap<>();
		param.put("startIndex", startIndex);
		param.put("size", size);
		
		List<ManageUserDTO> userList = manageuserMapper.selectUserList(param);
		
		Map<String, Object> resultList = new HashMap<>();
		resultList.put("userList", userList);
		resultList.put("pageNo", page);
		resultList.put("size", size);
		
		return resultList;
	}


	@Override
	public Map<String, Object> getOwnerListByAdmin(int pageNo, int size) {
		int startIndex = pagenationService.getStartIndex(pageNo, size);
		
		Map<String, Object> param = new HashMap<>();
		param.put("startIndex", startIndex);
		param.put("size", size);
		
		List<ManageUserDTO> userList = manageuserMapper.selectUserList(param);
		
		Map<String, Object> resultList = new HashMap<>();
		resultList.put("userList", userList);
		resultList.put("pageNo", pageNo);
		resultList.put("size", size);
		
		return resultList;
	}
    
	
}

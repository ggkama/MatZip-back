package com.kh.matzip.admin.manageUser.model.servcie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.admin.manageUser.model.dao.ManageUserMapper;
import com.kh.matzip.admin.manageUser.model.dto.ManageUserDTO;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.EntityNotFoundException;
import com.kh.matzip.global.error.exceptions.UserAlreadyDeletedException;
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
		int totalUsers = manageuserMapper.countAllUsers();
		
		Map<String, Object> resultList = new HashMap<>();
		resultList.put("userList", userList);
		resultList.put("pageNo", page);
		resultList.put("size", size);
		resultList.put("totalUsers", totalUsers);
		resultList.put("totalPages", (int) Math.ceil((double) totalUsers / size));
		
		return resultList;
	}

	@Override
	public ManageUserDTO getUserDetailByAdmin(Long userNo) {
		
		ManageUserDTO user = manageuserMapper.selectUserDetail(userNo);

	    if (user == null) {
	        throw new EntityNotFoundException(ResponseCode.USER_NOT, "회원이 없습니다.");
	    }

	    return user;
	}
	
	@Override
	public void unregisterUserByAdmin(Long userNo) {
		ManageUserDTO user = manageuserMapper.selectUserDetail(userNo);
		
		if(user == null) {
			throw new EntityNotFoundException(ResponseCode.USER_NOT, "회원이 없습니다.");
		}
		
		if ("Y".equals(user.getIsDeleted())) {
	        throw new UserAlreadyDeletedException(ResponseCode.USER_IS_DELETED, "이미 탈퇴된 회원입니다.");
	    }
		
		int result = manageuserMapper.updateStatusUser(userNo);
		
		if (result == 0) {
	        throw new EntityNotFoundException(ResponseCode.USER_NOT, "회원이 없습니다.");
	    }
	}
    
}

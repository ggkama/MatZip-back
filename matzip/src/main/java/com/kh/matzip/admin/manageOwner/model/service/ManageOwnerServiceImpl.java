package com.kh.matzip.admin.manageOwner.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.admin.manageOwner.model.dao.ManageOwnerMapper;
import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerDetailDTO;
import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerListDTO;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.DatabaseOperationException;
import com.kh.matzip.global.error.exceptions.DuplicateDataException;
import com.kh.matzip.global.error.exceptions.EntityNotFoundException;
import com.kh.matzip.util.pagenation.PagenationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageOwnerServiceImpl implements ManageOwnerService {

	private final ManageOwnerMapper manageownerMapper;
	private final PagenationService pagenationService;
	
	@Override
	public Map<String, Object> getOwnerListByAdmin(int page, int size) {
		int startIndex = pagenationService.getStartIndex(page, size);
		
		Map<String, Object> param = new HashMap<>();
		param.put("startIndex", startIndex);
		param.put("size", size);
		
		List<ManageOwnerListDTO> ownerList = manageownerMapper.selectOwnerList(param);
		int totalOwners = manageownerMapper.countAllStores();
		
		Map<String, Object> resultList = new HashMap<>();
		resultList.put("ownerList", ownerList);
		resultList.put("pageNo", page);
		resultList.put("size", size);
		resultList.put("totalOwners", totalOwners);
		resultList.put("totalPages", (int) Math.ceil((double) totalOwners / size));
		
		return resultList;
	}
    
	@Override
	public ManageOwnerDetailDTO getOwnerDetailByAdmin(Long registerNo) {
		
		ManageOwnerDetailDTO ownerUser = manageownerMapper.selectOwnerDetail(registerNo);
		
		if(ownerUser == null) {
			throw new EntityNotFoundException(ResponseCode.USER_NOT, "회원이 없습니다.");
		}
		return ownerUser;
	}

	@Override
	public void approveOwner(Long registerNo) {
	
		String currentStatus = manageownerMapper.findStatusByRegisterNo(registerNo);
		
		if("Y".equals(currentStatus)) {
			throw new DuplicateDataException(ResponseCode.DUPLICATIED_APPROVAL, "이미 관리자가 확인한 신청건 입니다.");
		}
		
		int updated = manageownerMapper.updateStatusOwner(registerNo);
	    if (updated == 0) {
	        throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "신청 정보가 존재하지 않습니다.");
	    }

	    int roleUpdated = manageownerMapper.updateUserRoleByRegisterNo(registerNo);
	    if (roleUpdated == 0) {
	        throw new DatabaseOperationException(ResponseCode.FAIL_CHANGE_OWNER, "사용자 권한 업데이트에 실패했습니다.");
	    }

	}

	@Override
	public void rejectOwner(Long registerNo) {
		
		String currentStatus = manageownerMapper.findStatusByRegisterNo(registerNo);

	    if ("Y".equals(currentStatus)) {
	        throw new DuplicateDataException(ResponseCode.DUPLICATIED_APPROVAL, "이미 승인된 신청건은 반려할 수 없습니다.");
	    }
	    
	    int updated = manageownerMapper.updateStatusOwner(registerNo);
	    if (updated == 0) {
	        throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "신청 정보가 존재하지 않습니다.");
	    }
	}
	
}

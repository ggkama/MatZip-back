package com.kh.matzip.admin.manageStore.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.admin.manageStore.model.dao.ManageStoreMapper;
import com.kh.matzip.admin.manageStore.model.dto.ManageStoreListDTO;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.DuplicateDataException;
import com.kh.matzip.global.error.exceptions.EntityNotFoundException;
import com.kh.matzip.util.pagenation.PagenationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageStoreServiceImpl implements ManageStoreService {
	
	private final ManageStoreMapper managestoreMapper;
	private final PagenationService pagenationService;
	
	@Override
	public Map<String, Object> getStoreListByAdmin(int page, int size) {

        int startIndex = pagenationService.getStartIndex(page, size);

        Map<String, Object> param = new HashMap<>();
        param.put("startIndex", startIndex);
        param.put("size", size);

        List<ManageStoreListDTO> storeList = managestoreMapper.selectStoreList(param);
        int totalStores = managestoreMapper.countAllStore();

        Map<String, Object> result = new HashMap<>();
        result.put("storeList", storeList);
        result.put("pageNo", page);
        result.put("size", size);
        result.put("totalStores", totalStores);
        result.put("totalPages", (int) Math.ceil((double) totalStores / size));

        return result;
	}

	@Override
	public void deleteStore(Long storeNo) {
		int result = managestoreMapper.deleteStore(storeNo);
		
		if (result == 0) {
            throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "해당 매장이 존재하지 않습니다.");
        }
		
	}
	
	
}
    

package com.kh.matzip.admin.manageStore.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.admin.manageStore.model.service.ManageStoreService;
import com.kh.matzip.global.error.exceptions.StoreNotFoundException;
import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.store.model.dto.StoreDTO;
import com.kh.matzip.store.model.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/manage/stores")
@RequiredArgsConstructor
public class ManageStoreController {

    private final ManageStoreService manageStoreService;
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<?> getStoreListByAdmin(
    		@AuthenticationPrincipal CustomUserDetails user,
    		@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
    	
        Map<String, Object> result = manageStoreService.getStoreListByAdmin(page, size);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/detail/{storeNo}")
    public ResponseEntity<StoreDTO> getStoreDetailByAdmin(@PathVariable(name = "storeNo") Long storeNo) {
    	
        StoreDTO store = storeService.getStoreDetail(storeNo);
        if (store == null) {
        	throw new StoreNotFoundException();
        }
        return ResponseEntity.ok(store);
    }
    
    @DeleteMapping("/{storeNo}")
    public ResponseEntity<?> deleteStoreByAdmin(@PathVariable(name = "storeNo") Long storeNo) {
    	manageStoreService.deleteStore(storeNo);
        return ResponseEntity.ok("매장이 성공적으로 삭제되었습니다.");
    }

}
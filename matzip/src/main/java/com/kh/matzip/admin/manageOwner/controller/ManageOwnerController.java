package com.kh.matzip.admin.manageOwner.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.admin.manageOwner.model.service.ManageOwnerService;
import com.kh.matzip.member.model.vo.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class ManageOwnerController {
    
	private final ManageOwnerService manageownerService;
	
	@GetMapping("/manage/ownerList")
	public ResponseEntity<?> getAdminUserList(
			@AuthenticationPrincipal CustomUserDetails user,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		System.out.println(">> 현재 로그인한 사용자 번호(userNo): " + user.getUserNo());
		Map<String, Object> result = manageownerService.getOwnerListByAdmin(page, size);
		return ResponseEntity.ok(result);
	}
	
}

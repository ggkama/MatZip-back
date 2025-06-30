package com.kh.matzip.admin.manageUser.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.admin.manageUser.model.servcie.ManageUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class ManageUserController {
	
	private final ManageUserService manageuserService;
    
	@GetMapping("/manage/userList")
	public ResponseEntity<?> getAdminUserList(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		Map<String, Object> result = manageuserService.getUserListByAdmin(page, size);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/manage/ownerList")
	public ResponseEntity<?> getAdminOwnerList(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		Map<String, Object> result = manageuserService.getUserListByAdmin(page, size);
		return ResponseEntity.ok(result);
	}
	
}

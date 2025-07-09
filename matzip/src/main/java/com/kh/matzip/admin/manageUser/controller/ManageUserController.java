package com.kh.matzip.admin.manageUser.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.admin.manageUser.model.dto.ManageUserDTO;
import com.kh.matzip.admin.manageUser.model.servcie.ManageUserService;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.vo.CustomUserDetails;

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
			@AuthenticationPrincipal CustomUserDetails user,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		System.out.println(">> 현재 로그인한 사용자 번호(userNo): " + user.getUserNo());
		Map<String, Object> result = manageuserService.getUserListByAdmin(page, size);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/manage/user/detail")
	public ResponseEntity<ApiResponse<ManageUserDTO>> getUserDetail(@RequestBody Map<String, Long> request) {
	    Long userNo = request.get("userNo");

	    ManageUserDTO user = manageuserService.getUserDetailByAdmin(userNo);

	    return ResponseEntity.ok(
	        ApiResponse.success(ResponseCode.USER_DETAIL_SUCCESS, user, "사용자 조회에 성공했습니다.")
	    );
	}
	
	@PostMapping("/manage/user/delete")
	public ResponseEntity<ApiResponse<?>> unregisterUser(@RequestBody Map<String, Long> request) {
	    Long userNo = request.get("userNo");

	    manageuserService.unregisterUserByAdmin(userNo);

	    return ResponseEntity.ok(
	        ApiResponse.success(ResponseCode.USER_UNREGISTER, "사용자 탈퇴가 성공적으로 처리되었습니다.")
	    );
	}
	
}

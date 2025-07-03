package com.kh.matzip.admin.manageOwner.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.admin.manageOwner.model.dto.ManageOwnerDetailDTO;
import com.kh.matzip.admin.manageOwner.model.service.ManageOwnerService;
import com.kh.matzip.admin.manageUser.model.dto.ManageUserDTO;
import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
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
	
	@PostMapping("/manage/owner/detail")
	public ResponseEntity<ApiResponse<ManageOwnerDetailDTO>> getOwnerDetail(@RequestBody Map<String, Long> request){
		Long registerNo = request.get("registerNo");

	    ManageOwnerDetailDTO ownerUser = manageownerService.getOwnerDetailByAdmin(registerNo);

	    return ResponseEntity.ok(
	        ApiResponse.success(ResponseCode.OWNER_DETAIL_SUCCESS, ownerUser, "사용자 조회에 성공했습니다.")
	    );
	}
	
	@PostMapping("/manage/owner/approve")
	public ResponseEntity<ApiResponse<Void>> approveOwner(@RequestBody Map<String, Long> request) {
	    Long registerNo = request.get("registerNo");

	    manageownerService.approveOwner(registerNo);

	    return ResponseEntity.ok(
	        ApiResponse.success(ResponseCode.OWNER_APPROVE, "사장님 권한이 승인되었습니다.")
	    );
	}

	@PostMapping("/manage/owner/reject")
	public ResponseEntity<ApiResponse<Void>> rejectOwner(@RequestBody Map<String, Long> request) {
	    Long registerNo = request.get("registerNo");

	    manageownerService.rejectOwner(registerNo);

	    return ResponseEntity.ok(
	        ApiResponse.success(ResponseCode.OWNER_REJECT, "사장님 권한이 반려되었습니다.")
	    );
	}
	
}

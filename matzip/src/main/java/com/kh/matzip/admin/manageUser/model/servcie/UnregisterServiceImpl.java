package com.kh.matzip.admin.manageUser.model.servcie;

import org.springframework.stereotype.Service;

import com.kh.matzip.admin.manageUser.model.dao.ManageUserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnregisterServiceImpl implements UnregisterService {
	
	private final ManageUserMapper manageuserMapper;
	
	// 신청자의 사용자 권한이 ROLE_USER인지 확인
	
	// 이미 ROLE_OWNER인 경우 예외 처리
	
	/* 사용자의 ROLE 확인 */
	@Override
	public void updateStatusUser(String userNo) {
		
	}
	
}

package com.kh.matzip.mypage.model.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.InvalidPasswordException;
import com.kh.matzip.global.error.exceptions.UpdateFailedException;
import com.kh.matzip.mypage.model.dao.MyProfileMapper;
import com.kh.matzip.mypage.model.dto.MyProfileDTO;
import com.kh.matzip.store.model.dao.StoreMapper;
import com.kh.matzip.util.file.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MyProfileServiceImpl implements MyProfileService {

    private final PasswordEncoder passwordEncoder;
    
    private final MyProfileMapper myProfileMapper;
    private final FileService fileService;


    @Override
    public MyProfileDTO selectMyProfile(Long userNo) {
        return myProfileMapper.selectMyProfile(userNo);
    }

    @Override
    @Transactional
    public void updateMyProfile(MyProfileDTO dto, MultipartFile profileImage) {
        // 1. 기본 정보 수정
        int updated = updateUserInfo(dto);
        if (updated == 0) {
            throw new UpdateFailedException("회원 정보 수정 실패");
        }

        // 2. 프로필 이미지 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            String oldImageUrl = myProfileMapper.findImageByUserNo(dto.getUserNo());
            String newImageUrl = fileService.store(profileImage); // 새 이미지 저장

            Map<String, Object> imageData = Map.of(
                "userNo", dto.getUserNo(),
                "imageUrl", newImageUrl
            );

            if (oldImageUrl != null) {
                // 기존 이미지 삭제 + DB 업데이트
                fileService.delete(oldImageUrl);
                myProfileMapper.updateProfileImage(imageData);
            } else {
                myProfileMapper.insertProfileImage(imageData);
            }
        }
    }

    private int updateUserInfo(MyProfileDTO dto) {
        try {
            myProfileMapper.updateUserProfile(dto);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

   @Override
    public void updatePassword(Long userNo, String currentPw, String newPw) {
        String userPw = myProfileMapper.findPasswordByUserNo(userNo);

        if (!passwordEncoder.matches(currentPw, userPw)) {
            throw new InvalidPasswordException(ResponseCode.INVALID_PASSWORD, "현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedNewPw = passwordEncoder.encode(newPw);

        Map<String, Object> param = new HashMap<>();
        param.put("userNo", userNo);
        param.put("userPw", encodedNewPw);

        myProfileMapper.updatePassword(param);
    }

   @Override
    public void deleteUser(Long userNo, String inputPw) {
        // 1. DB에서 사용자 비밀번호 조회
        String encodedPw = myProfileMapper.findPasswordByUserNo(userNo);
        
        // 2. 비밀번호 불일치 시 예외 던지기
        if (!passwordEncoder.matches(inputPw, encodedPw)) {
           throw new InvalidPasswordException(ResponseCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다.");

        }

        // 3. 사용자 탈퇴 처리
        myProfileMapper.deleteUser(userNo);

        // 4. 사장님일 경우 가게도 삭제
        String role = myProfileMapper.getUserRole(userNo);
        if ("ROLE_OWNER".equals(role)) {
            myProfileMapper.deleteStore(userNo);
        }
    }


    
}

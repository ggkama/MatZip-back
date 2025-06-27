package com.kh.matzip.store.model.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.global.error.exceptions.StoreAlreadyExistsException;
import com.kh.matzip.member.model.service.MemberService;
import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.store.model.dao.StoreMapper;
import com.kh.matzip.store.model.dto.StoreDTO;
import com.kh.matzip.util.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreMapper storeMapper;
    private final FileService fileService;
    private final MemberService memberService;
    

    /**
     * 매장 등록
     * @param storeDto 매장 정보
     * @param images 매장 이미지
     */
    @Override
    @Transactional
    public void createStore(StoreDTO storeDto, MultipartFile[] images) {  
        
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        // ROLE_OWNER가 아닌 경우 예외 발생
        if (!user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
            throw new AccessDeniedException("사장님 계정(ROLE_OWNER)만 매장을 등록할 수 있습니다.");
        }
        
        Long userNo = user.getUserNo();
        storeDto.setUserNo(userNo);

        // 2. 중복 매장 
        int count = storeMapper.countStoreByOwnerAndName(userNo, storeDto.getStoreName());
        if (count > 0) {
            throw new StoreAlreadyExistsException("이미 등록된 매장입니다.");
        }

        // 3. 매장 등록
        storeMapper.insertStore(storeDto); // storeNo가 selectKey로 자동 주입됨

        Long storeNo = storeDto.getStoreNo();
      
        // 4. 이미지 저장
        if (images == null || images.length == 0) {
            throw new IllegalArgumentException("이미지는 최소 1장 이상 등록해야 합니다.");
        }
        if (images.length > 5) {
            throw new IllegalArgumentException("이미지는 최대 5장까지 등록할 수 있습니다.");
        }
        for (MultipartFile image : images) {
            String savedPath = fileService.store(image);
            storeMapper.insertStoreImage(storeNo, savedPath);
        }
        
        // 5. 편의시설 저장
        if (storeDto.getCategoryConvenience() != null) {
            for (String convenience : storeDto.getCategoryConvenience()) {
                storeMapper.insertStoreConvenience(storeNo, convenience);
            }
        }

        // 7. 휴무일 저장
        if (storeDto.getDayOff() != null) {
            for (String offDay : storeDto.getDayOff()) {
                storeMapper.insertDayOff(storeNo, offDay);
            }
        }
    }

    
}
package com.kh.matzip.store.model.service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.store.model.dto.StoreDTO;

public interface StoreService {
    
    void insertStore(CustomUserDetails user, StoreDTO storeDto, MultipartFile[] images);

    boolean existsStoreByUserNo(Long userNo);

    StoreDTO getStoreByUserNo(Long userNo);

    void updateStore(
        CustomUserDetails user,
        StoreDTO storeDto,
        MultipartFile[] images,
        List<String> deletedImagePaths,
        List<String> changedOldImages,        
        List<MultipartFile> changedNewImages  
    );

<<<<<<< HEAD
=======
    // 전체 가게 리스트(검색/페이징)
    Map<String, Object> getStoreList(int page, int size, String search);

    // (선택) 가게 상세 정보 (가게번호로)
    // StoreDTO getStoreDetail(Long storeNo);

>>>>>>> 2286a98259e30380aaed52c7c439f227e1b66df3
}

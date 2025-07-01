package com.kh.matzip.store.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.store.model.dto.StoreDTO;

public interface StoreService {
    
    void insertStore(CustomUserDetails user, StoreDTO storeDto, MultipartFile[] images);

    boolean existsStoreByUserNo(Long userNo);
}

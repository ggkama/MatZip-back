package com.kh.matzip.store.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.store.model.dto.StoreDTO;

public interface StoreService {
    
    void createStore(StoreDTO storeDto, MultipartFile[] images);

}

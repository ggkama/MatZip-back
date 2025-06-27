package com.kh.matzip.store.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.store.model.dto.StoreDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    
    public void createStore(StoreDTO storeDto, MultipartFile[] images, String token) {
        
    }
}

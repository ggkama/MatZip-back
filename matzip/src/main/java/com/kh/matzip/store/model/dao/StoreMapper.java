package com.kh.matzip.store.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.store.model.dto.StoreDTO;

@Mapper
public interface StoreMapper {
    
    void insertStore(StoreDTO storeDto);

}

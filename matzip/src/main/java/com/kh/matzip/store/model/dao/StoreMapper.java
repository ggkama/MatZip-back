package com.kh.matzip.store.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.kh.matzip.store.model.dto.StoreDTO;

@Mapper
public interface StoreMapper {

    void insertStore(StoreDTO storeDto);

    // 이미지 등록
    void insertStoreImage(@Param("storeNo") Long storeNo, @Param("image") String image);

    // 매장 편의시설 등록
    void insertStoreConvenience(@Param("storeNo") Long storeNo, @Param("convenience") String convenience);

    // 매장 쉬는 날 등록
    void insertDayOff(@Param("storeNo") Long storeNo, @Param("offDay") String offDay);

    // 중복 매장명 확인
    int countStoreByOwnerAndName(@Param("ownerNo") Long ownerNo, @Param("storeName") String storeName);
}

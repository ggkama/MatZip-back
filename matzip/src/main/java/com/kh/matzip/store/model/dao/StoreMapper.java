package com.kh.matzip.store.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.store.model.dto.StoreDTO;

@Mapper
public interface StoreMapper {

    void insertStore(StoreDTO storeDto);

    // 이미지 등록
    void insertStoreImage(Map<String, Object> params);

    // 매장 편의시설 등록
    void insertStoreConvenience(Map<String, Object> params);

    // 매장 쉬는 날 등록
    void insertDayOff(Map<String, Object> params);

    // 임시 쉬는 날 등록
    void insertShutdownDay(Map<String, Object> params);  // 새 메서드 추가

    // 중복 매장명 확인
    int countStoreByUserNo(Map<String, Object> params);

    // 메뉴 등록
    int insertMenu(Map<String, Object> map);
}
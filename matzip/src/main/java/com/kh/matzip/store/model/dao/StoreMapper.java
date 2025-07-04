package com.kh.matzip.store.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.store.model.dto.StoreDTO;

@Mapper
public interface StoreMapper {

    // 매장
    void insertStore(StoreDTO storeDto);
    void updateStore(StoreDTO storeDto);
    int countStoreByOwnerAndName(Map<String, Object> params);
    boolean existsStoreByUserNo(Long userNo);
    StoreDTO selectStoreByUserNo(Long userNo);

    // 이미지
    void insertStoreImage(Map<String, Object> params);
    List<String> selectStoreImagesByStoreNo(Long storeNo);
    void deleteStoreImageByPath(Map<String, Object> params);
    void updateStoreImage(Map<String, Object> params);

    // 편의시설
    void insertStoreConvenience(Map<String, Object> params);
    List<String> selectConveniencesByStoreNo(Long storeNo);
    void deleteSingleConvenience(Map<String, Object> params);
    void updateStoreConvenience(Map<String, Object> params);

    // 쉬는 날
    void insertDayOff(Map<String, Object> params);
    List<String> selectDayOffByStoreNo(Long storeNo);
    void deleteSingleDayOff(Map<String, Object> params);
    void updateDayOff(Map<String, Object> params);

    // 임시 쉬는 날
    void insertShutdownDay(Map<String, Object> params);
    Map<String, Object> selectShutdownDayByStoreNo(Long storeNo);
    void deleteShutdownDayByStoreNo(Long storeNo);
    void updateShutdownDay(Map<String, Object> params);

    // 메뉴
    int insertMenu(Map<String, Object> params);
    List<String> selectMenuByStoreNo(Long storeNo);
    void deleteSingleMenu(Map<String, Object> params);
    void updateMenu(Map<String, Object> params);

    // 리스트 조회
    List<StoreDTO> selectStoreList(Map<String, Object> param);
    long selectStoreListCount(Map<String, Object> param);
    

    // 스토어 디테일 조회
    StoreDTO selectStoreByStoreNo(Long storeNo);
    StoreDTO getStoreDetail(Long storeNo);
}

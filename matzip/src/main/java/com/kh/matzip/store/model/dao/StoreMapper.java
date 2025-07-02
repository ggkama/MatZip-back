package com.kh.matzip.store.model.dao;

import java.util.Date;
import java.util.Map;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.kh.matzip.store.model.dto.StoreDTO;

@Mapper
public interface StoreMapper {

   void insertStore(StoreDTO storeDto);
    void updateStore(StoreDTO storeDto);

    // 이미지
    void insertStoreImage(Map<String, Object> params);
    List<String> selectStoreImagesByStoreNo(Long storeNo);
    void deleteStoreImageByPath(@Param("storeNo") Long storeNo, @Param("image") String imagePath);

    // 이미지 
     void updateStoreImage(@Param("storeNo") Long storeNo,
                          @Param("oldImage") String oldImage,
                          @Param("newImage") String newImage);
    // 편의시설
    void insertStoreConvenience(Map<String, Object> params);
    List<String> selectConveniencesByStoreNo(Long storeNo);
    void deleteSingleConvenience(@Param("storeNo") Long storeNo, @Param("convenience") String convenience);

    void updateStoreConvenience(@Param("storeNo") Long storeNo,
                                @Param("oldConvenience") String oldConvenience,
                                @Param("newConvenience") String newConvenience);

    // 쉬는 날
    void insertDayOff(Map<String, Object> params);
    List<String> selectDayOffByStoreNo(Long storeNo);
    void deleteSingleDayOff(@Param("storeNo") Long storeNo, @Param("offDay") String offDay);

    void updateDayOff(@Param("storeNo") Long storeNo,
                      @Param("oldOffDay") String oldOffDay,
                      @Param("newOffDay") String newOffDay);

    // 임시 쉬는 날
    void insertShutdownDay(Map<String, Object> params);
    Map<String, Object> selectShutdownDayByStoreNo(Long storeNo);
    void deleteShutdownDayByStoreNo(Long storeNo);

    void updateShutdownDay(@Param("storeNo") Long storeNo,
                       @Param("dayNo") Long dayNo,
                       @Param("startDate") Date startDate,
                       @Param("endDate") Date endDate);

    // 메뉴
    int insertMenu(Map<String, Object> params);
    List<String> selectMenuByStoreNo(Long storeNo);
    void deleteSingleMenu(@Param("storeNo") Long storeNo, @Param("menuName") String menuName);
    

    void updateMenu(@Param("storeNo") Long storeNo,
                    @Param("oldMenu") String oldMenu,
                    @Param("newMenu") String newMenu);


    // 매장 관련
    int countStoreByOwnerAndName(Map<String, Object> params);
    boolean existsStoreByUserNo(@Param("userNo") Long userNo);
    StoreDTO selectStoreByUserNo(Long userNo);
}
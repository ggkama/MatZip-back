package com.kh.matzip.reservation.model.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.reservation.model.dto.ReservationDTO;

@Mapper
public interface ReservationMapper {

    List<String> selectDayOffByStoreNo(Long storeNo);

    Map<String, Object> selectShutdownDayByStoreNo(Long storeNo);

    Map<String, String> selectOpenCloseTimeByStoreNo(Long storeNo);

    void createReservation(ReservationDTO reservation);
}

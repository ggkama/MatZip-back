package com.kh.matzip.reservation.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.reservation.model.dto.ReservationDTO;

@Mapper
public interface ReservationMapper {
    void createReservation(ReservationDTO reservation);
}

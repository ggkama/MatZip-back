package com.kh.matzip.reservation.model.service;

import java.util.List;

import com.kh.matzip.reservation.model.dto.ReservationCancelDTO;
import com.kh.matzip.reservation.model.dto.ReservationDTO;


public interface ReservationService {

    ReservationDTO getReservationInfoStoreNo(Long storeNo);

    void createReservation(ReservationDTO reservation);

    List<ReservationDTO> getReservationUserNo(Long userNo);

    ReservationDTO findByReservationNo(Long reservationNo);

    void cancelReservation(ReservationCancelDTO dto);
}

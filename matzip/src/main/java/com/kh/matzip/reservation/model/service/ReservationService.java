package com.kh.matzip.reservation.model.service;

import java.util.Map;

import com.kh.matzip.reservation.model.dto.ReservationCancelDTO;
import com.kh.matzip.reservation.model.dto.ReservationDTO;


public interface ReservationService {

    ReservationDTO getReservationInfoStoreNo(Long storeNo);

    void createReservation(ReservationDTO reservation);

    Map<String, Object> getReservationsByUserNo(Long userNo, int page, int size);

    ReservationDTO findByReservationNo(Long reservationNo);

    void cancelReservation(ReservationCancelDTO dto);

    void updateIsReviewYet();

    void updateIsReviewComplete(Long reservationNo);

   public Map<String, Object> getReservationsByStoreNo(Long storeNo, int page, int size);

    ReservationDTO getReservationDetailByNo(Long reservationNo);

    void cancelReservationOwner(ReservationCancelDTO dto);
}

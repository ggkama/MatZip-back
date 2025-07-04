package com.kh.matzip.reservation.model.service;

import com.kh.matzip.reservation.model.dto.ReservationDTO;


public interface ReservationService {

    ReservationDTO getReservationInfoStoreNo(Long storeNo);

    void createReservation(ReservationDTO reservation);

    
}

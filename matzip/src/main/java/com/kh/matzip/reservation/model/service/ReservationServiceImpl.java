package com.kh.matzip.reservation.model.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.kh.matzip.reservation.model.dao.ReservationMapper;
import com.kh.matzip.reservation.model.dto.ReservationDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationMapper reservationMapper; 

  @Override
    public ReservationDTO getReservationInfoStoreNo(Long storeNo) {
        ReservationDTO dto = new ReservationDTO();

        // 1. 운영시간 + 휴무일 + 임시휴무일 등 예약 조건 관련 정보 조회
        List<String> dayOff = reservationMapper.selectDayOffByStoreNo(storeNo);
        Map<String, Object> shutdown = reservationMapper.selectShutdownDayByStoreNo(storeNo);
        Map<String, Object> openClose = reservationMapper.selectOpenCloseTimeByStoreNo(storeNo);

        dto.setStoreNo(storeNo);
        dto.setDayOff(dayOff);

        if (openClose != null && !openClose.isEmpty()) {
            dto.setOpenTime((String) openClose.get("OPEN_TIME"));
            dto.setCloseTime((String) openClose.get("CLOSE_TIME"));
            dto.setStoreName((String) openClose.get("STORE_NAME")); // 수정된 부분
        }

        if (shutdown != null && !shutdown.isEmpty()) {
            dto.setStartDate((Date) shutdown.get("START_DATE"));
            dto.setEndDate((Date) shutdown.get("END_DATE"));
        }

        return dto;
    }

   @Override
  public void createReservation(ReservationDTO reservation) {
      reservationMapper.createReservation(reservation);
  }
    
}

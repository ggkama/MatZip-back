package com.kh.matzip.reservation.model.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.reservation.model.dao.ReservationMapper;
import com.kh.matzip.reservation.model.dto.ReservationCancelDTO;
import com.kh.matzip.reservation.model.dto.ReservationDTO;

import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public void createReservation(ReservationDTO reservation) {
        reservationMapper.createReservation(reservation);
    }

    @Override
    public List<ReservationDTO> getReservationUserNo(Long userNo) {
        return reservationMapper.getReservationUserNo(userNo);
    }

    @Override
    public ReservationDTO findByReservationNo(Long reservationNo) {
        return reservationMapper.findByReservationNo(reservationNo);
    }


    @Override
    @Transactional
    public void cancelReservation(ReservationCancelDTO dto) {
        // 1. TB_RESERVATION 상태 'N'으로 변경
        int result1 = reservationMapper.cancelReservationStatus(dto.getReservationNo());
        if (result1 == 0) {
            throw new RuntimeException("예약 상태 변경 실패");
        }

        // 2. TB_RESERVATION_CANCEL에 취소 정보 삽입
        int result2 = reservationMapper.insertCancelInfo(dto);
        if (result2 == 0) {
            throw new RuntimeException("예약 취소 정보 저장 실패");
        }
    }

    @Override
    public void updateIsReviewYet() {
        reservationMapper.updateIsReviewYet();
    }

    @Override
    public void updateIsReviewComplete(Long reservationNo) {
        reservationMapper.updateIsReviewComplete(reservationNo);
    }

    // 사장님 예약 조회
    @Override
    public List<ReservationDTO> getReservationsByStoreNo(Long storeNo) {
        return reservationMapper.getReservationsByStoreNo(storeNo);
    }

    // 사장님 예약 상세 조회

    @Override
    public ReservationDTO getReservationDetailByNo(Long reservationNo) {
        return reservationMapper.getReservationDetailByNo(reservationNo);
    }

    // 사장님 예약취소
    @Transactional
    @Override
    public void cancelReservationOwner(ReservationCancelDTO dto) {
        Long reservationNo = dto.getReservationNo();
        String cancelReason = dto.getCancelReason();

        reservationMapper.updateReservationStatusToCancel(reservationNo);

        Map<String, Object> param = new HashMap<>();
        param.put("reservationNo", reservationNo);
        param.put("cancelReason", cancelReason);
        reservationMapper.insertReservationCancel(param);
    }
    
}

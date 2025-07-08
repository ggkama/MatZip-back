package com.kh.matzip.reservation.model.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.global.error.exceptions.InvalidReservationException;
import com.kh.matzip.reservation.model.dao.ReservationMapper;
import com.kh.matzip.reservation.model.dto.ReservationCancelDTO;
import com.kh.matzip.reservation.model.dto.ReservationDTO;
import com.kh.matzip.util.pagenation.PagenationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationMapper reservationMapper; 
  private final PagenationService pagenationService;

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
    public Map<String, Object> getReservationsByUserNo(Long userNo, int page, int size) {
        int startIndex = pagenationService.getStartIndex(page, size);

        Map<String, Object> param = new HashMap<>();
        param.put("userNo", userNo);
        param.put("startIndex", startIndex);
        param.put("size", size);

        List<ReservationDTO> list = reservationMapper.getReservationUserNo(param);
        int total = reservationMapper.countReservationsByUserNo(userNo);

        Map<String, Object> result = new HashMap<>();
        result.put("reservationList", list);
        result.put("pageNo", page);
        result.put("size", size);
        result.put("totalReservations", total);
        result.put("totalPages", (int) Math.ceil((double) total / size));

        return result;
    }

    // 예약가능 인원수 체크

   @Override
    public Long getAvailablePersonCount(Map<String, Object> param) {
        // 1. 필수 파라미터 유효성 체크
        if (!param.containsKey("storeNo") || !param.containsKey("reservationDate") || !param.containsKey("reservationTime")) {
            throw new InvalidReservationException("필수 파라미터가 누락되었습니다.");
        }

        Long storeNo;
        String dateStr;
        String timeStr;

        try {
            storeNo = Long.parseLong(String.valueOf(param.get("storeNo")));
            dateStr = String.valueOf(param.get("reservationDate"));
            timeStr = String.valueOf(param.get("reservationTime"));
        } catch (Exception e) {
            throw new InvalidReservationException("파라미터 형식이 올바르지 않습니다.");
        }

        // 2. 날짜/시간 포맷 확인
        try {
            LocalDate.parse(dateStr); // yyyy-MM-dd 형식
            LocalTime.parse(timeStr); // HH:mm 형식
        } catch (DateTimeParseException e) {
            throw new InvalidReservationException("날짜 또는 시간 형식이 잘못되었습니다.");
        }

        // 3. Mapper 호출
        Long result = reservationMapper.getAvailablePersonCount(param);

        // 4. 결과 반환 (null 방지)
        return result != null ? result : 0L;
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
    public Map<String, Object> getReservationsByStoreNo(Long storeNo, int page, int size) {
        int startIndex = pagenationService.getStartIndex(page, size);

        Map<String, Object> param = new HashMap<>();
        param.put("storeNo", storeNo);
        param.put("startIndex", startIndex);
        param.put("size", size);

        List<ReservationDTO> reservationList = reservationMapper.getReservationsByStoreNo(param);
        int totalReservations = reservationMapper.countReservationsByStoreNo(storeNo);

        Map<String, Object> result = new HashMap<>();
        result.put("reservationList", reservationList);
        result.put("pageNo", page);
        result.put("size", size);
        result.put("totalReservations", totalReservations);
        result.put("totalPages", (int) Math.ceil((double) totalReservations / size));

        return result;
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
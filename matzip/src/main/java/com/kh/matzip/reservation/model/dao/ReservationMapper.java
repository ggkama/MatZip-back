package com.kh.matzip.reservation.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.reservation.model.dto.ReservationCancelDTO;
import com.kh.matzip.reservation.model.dto.ReservationDTO;

@Mapper
public interface ReservationMapper {

    List<String> selectDayOffByStoreNo(Long storeNo);

    Map<String, Object> selectShutdownDayByStoreNo(Long storeNo);

    Map<String, Object> selectOpenCloseTimeByStoreNo(Long storeNo);

    void createReservation(ReservationDTO reservation);

    // 인원수 체크
    Long getAvailablePersonCount(Map<String, Object> param);

    List<ReservationDTO> getReservationUserNo(Long userNo);

    ReservationDTO findByReservationNo(Long reservationNo);

    int cancelReservationStatus(Long reservationNo);

    int insertCancelInfo(ReservationCancelDTO dto);

    List<ReservationDTO> getReservationUserNo(Map<String, Object> param);
    int countReservationsByUserNo(Long userNo);

     //  예약일이 지났고 상태가 Y이며 리뷰가 없는 예약 → YET 상태로 변경
    int updateIsReviewYet();

    //  실제 리뷰가 존재할 경우 예약의 IS_REVIEW를 COM으로 변경
    int updateIsReviewComplete(Long reservationNo);

    // 사장님 예약 조회
    List<ReservationDTO> getReservationsByStoreNo(Map<String, Object> param);
    int countReservationsByStoreNo(Long storeNo);

    // 사장님 예약 상세 조회
    ReservationDTO getReservationDetailByNo(Long reservationNo);

    // 사장님 예약취소
    int updateReservationStatusToCancel(Long reservationNo); // TB_RESERVATION 업데이트
    int insertReservationCancel(Map<String, Object> param);  // TB_RESERVATION_CANCEL


    
}

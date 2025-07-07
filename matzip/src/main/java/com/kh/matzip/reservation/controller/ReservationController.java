package com.kh.matzip.reservation.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.reservation.model.dto.ReservationCancelDTO;
import com.kh.matzip.reservation.model.dto.ReservationDTO;
import com.kh.matzip.reservation.model.service.ReservationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

   private final ReservationService reservationService;

    @GetMapping("/store/{storeNo}")
    public ResponseEntity<ReservationDTO> getReservationInfoStoreNo(@PathVariable(name = "storeNo") Long storeNo) {
        ReservationDTO reservationInfo = reservationService.getReservationInfoStoreNo(storeNo);
        if (reservationInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reservationInfo);
    }
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO reservation) {
        reservationService.createReservation(reservation);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mypage/{userNo}")
    public ResponseEntity<Map<String, Object>> getMyReservations(
            @PathVariable(name = "userNo") Long userNo,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {

        Map<String, Object> result = reservationService.getReservationsByUserNo(userNo, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/mypage/detail/{reservationNo}")
    public ResponseEntity<ReservationDTO> findByReservationNo(@PathVariable(name = "reservationNo") Long reservationNo) {
        ReservationDTO detail = reservationService.findByReservationNo(reservationNo);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(detail);
    }
    
   @PatchMapping("/mypage/cancel")
    public ResponseEntity<Void> cancelReservation(@RequestBody ReservationCancelDTO cancelDTO) {
        try {
            reservationService.cancelReservation(cancelDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // 예약 시간이 지난 상태라면 IS_REVIEW를 YET으로 변경
    @PatchMapping("/review/status-update")
    public ResponseEntity<Void> updateReviewStatusToYet() {
        reservationService.updateIsReviewYet();
        return ResponseEntity.ok().build();
    }

    // 리뷰 작성 완료 시 IS_REVIEW를 COM으로 변경
    @PatchMapping("/review/complete/{reservationNo}")
    public ResponseEntity<Void> updateReviewStatusToComplete(@PathVariable Long reservationNo) {
        reservationService.updateIsReviewComplete(reservationNo);
        return ResponseEntity.ok().build();
    }

    // 사장님 예약 조회
    @GetMapping("/owner/{storeNo}")
    public ResponseEntity<Map<String, Object>> getStoreReservations(
            @PathVariable Long storeNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> result = reservationService.getReservationsByStoreNo(storeNo, page, size);
        return ResponseEntity.ok(result);
    }

    // 사장님 예약 상세 조회 
    @GetMapping("/owner/detail/{reservationNo}")
        public ResponseEntity<ReservationDTO> getReservationDetailByNo(@PathVariable Long reservationNo) {
            ReservationDTO detail = reservationService.getReservationDetailByNo(reservationNo);
            return ResponseEntity.ok(detail);
    }

    // 사장님 예약 취소
    @PatchMapping("/owner/cancel")
    public ResponseEntity<Void> cancelReservationByOwner(@RequestBody ReservationCancelDTO cancelDTO) {
        try {
            reservationService.cancelReservationOwner(cancelDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
}
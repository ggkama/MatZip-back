package com.kh.matzip.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.reservation.model.dto.ReservationDTO;
import com.kh.matzip.reservation.model.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

   private final ReservationService reservationService;

    @GetMapping("/{storeNo}")
    public ResponseEntity<ReservationDTO> getReservationInfoStoreNo(@PathVariable("storeNo") Long storeNo) {
        ReservationDTO reservationInfo = reservationService.getReservationInfoStoreNo(storeNo);
        if (reservationInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reservationInfo);
    }
    // owner 조회/취소
    
}

package com.kh.matzip.reservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.reservation.model.dto.ReservationDTO;
import com.kh.matzip.reservation.model.service.ReservationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;

import com.kh.matzip.reservation.model.dto.ReservationCancelDTO;


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
    public ResponseEntity<List<ReservationDTO>> getMyReservations(@PathVariable(name = "userNo") Long userNo) {
        List<ReservationDTO> list = reservationService.getReservationUserNo(userNo);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/mypage/detail/{reservationNo}")
    public ResponseEntity<ReservationDTO> findByReservationNo(@PathVariable Long reservationNo) {
        ReservationDTO detail = reservationService.findByReservationNo(reservationNo);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(detail);
    }
    
   @PatchMapping("/cancel")
    public ResponseEntity<Void> cancelReservation(@RequestBody ReservationCancelDTO cancelDTO) {
        try {
            reservationService.cancelReservation(cancelDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    
}

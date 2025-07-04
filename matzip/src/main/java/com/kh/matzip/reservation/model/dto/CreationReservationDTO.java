package com.kh.matzip.reservation.model.dto;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreationReservationDTO {
    @NotNull(message = "예약 날짜는 필수입니다.")
    private Date reservationDate;

    @NotBlank(message = "예약 시간은 필수입니다.")
    private String reservationTime;

    @NotNull(message = "인원 수는 필수입니다.")
    @Min(value = 1, message = "최소 1명 이상 예약 가능합니다.")
    private Integer personCount;    
}

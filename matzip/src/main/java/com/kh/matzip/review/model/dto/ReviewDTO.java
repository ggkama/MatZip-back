package com.kh.matzip.review.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDTO {

    public Long reviewNo;
    public Long reservationNo;
    public Long userNo;

    @NotBlank
    public String reviewContent;
    @NotBlank
    public double storeGrade;
    public Date createDate;
}

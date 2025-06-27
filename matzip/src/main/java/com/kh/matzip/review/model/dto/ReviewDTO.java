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

    private  Long reviewNo;
    private  Long reservationNo;
    private Long userNo;

    @NotBlank
    private String reviewContent;
    @NotBlank
    private double storeGrade;
    private Date createDate;

    private String image;
    private Long imageNo;
}

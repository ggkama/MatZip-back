package com.kh.matzip.review.model.dto;

import java.sql.Date;
import java.util.List;

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
    private Long storeNo;

    @NotBlank
    private String reviewContent;
    private double storeGrade;
    private Date createDate;
    private List<String> imageUrls; 
}
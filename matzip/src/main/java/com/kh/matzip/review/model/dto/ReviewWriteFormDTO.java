package com.kh.matzip.review.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReviewWriteFormDTO {

    
    private Long reservationNo;

    
    private Long storeNo;

    @NotBlank
    private String reviewContent;

    private double storeGrade;

    private List<String> imageUrls;

    private String reviewDate;
}

package com.kh.matzip.owner.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterOwnerDTO {

	private Long registerNo;
	private Long userNo;
	private String businessNo;
	private String storeName;
	private String image;
	private String status;
	private Date requestDate;
	
}

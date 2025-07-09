package com.kh.matzip.admin.manageOwner.model.dto;

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
public class ManageOwnerDetailDTO {

	private Long userNo;
	private String userName;
	private String userId;
	
	private Long registerNo;
	private String businessNo;
	private String storeName;
	private Date requestDate;
	private String image;
	private String status;
	
}
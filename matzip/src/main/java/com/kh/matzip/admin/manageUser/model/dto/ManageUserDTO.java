package com.kh.matzip.admin.manageUser.model.dto;

import java.sql.Date;

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
public class ManageUserDTO {
    
	private Long userNo;
	private String userId;
	private String userName;
	private String userNickName;
	private String userPhone;
	private String userRole;
	private String isDeleted;
	private Date enrollDate;
	
}

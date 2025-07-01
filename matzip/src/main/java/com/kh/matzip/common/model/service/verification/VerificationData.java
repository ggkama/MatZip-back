package com.kh.matzip.common.model.service.verification;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationData {

	private Long emailNo;
	private String email;
	private String emailCode;
	private LocalDateTime createTime;

	public VerificationData(String emailCode, LocalDateTime createTime) {
		this.emailCode = emailCode;
		this.createTime = createTime;
	}
	
}

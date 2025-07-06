package com.kh.matzip.mypage.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class PasswordDTO {
    
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@._^])[A-Za-z\\d!@._^]{8,20}$",
        message = "비밀번호는 영문 대문자, 소문자, 숫자, 특수문자(!, @, ., _, ^)를 각각 하나 이상 포함해야합니다. "
    )
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword; 

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@._^])[A-Za-z\\d!@._^]{8,20}$",
        message = "비밀번호는 영문 대문자, 소문자, 숫자, 특수문자(!, @, ., _, ^)"
    )
    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String newPassword;
}

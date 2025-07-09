package com.kh.matzip.member.model.vo;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

	private Long userNo;
	private String userId;
	private String userPw;
	private String userName;
	private String userNickName;
	private String userPhone;
	private String userRole;
	private String isDeleted;
	private Date enrollDate;
	private Date modifiedDate;

	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public String getPassword() {
		return userPw;
	}

	public String getUserRole() {
        return userRole;
    }

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	


}

package com.kh.matzip.member.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.matzip.member.model.dao.MemberMapper;
import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.model.vo.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    	
        MemberDTO member = memberMapper.findByUserId(userId);

        if (member == null) {
            throw new UsernameNotFoundException("해당 유저가 존재하지 않습니다.");
        }

        return CustomUserDetails.builder()
                .userNo(member.getUserNo())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .userName(member.getUserName())
                .userNickName(member.getUserNickName())
                .userPhone(member.getUserPhone())
                .userRole(member.getUserRole())
                .isDeleted(member.getIsDeleted())
                .enrollDate(member.getEnrollDate())
                .modifiedDate(member.getModifiedDate())
                .build();
    }

}

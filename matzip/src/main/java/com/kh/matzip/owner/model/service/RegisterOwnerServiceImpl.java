package com.kh.matzip.owner.model.service;

import org.springframework.stereotype.Service;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.DuplicateDataException;
import com.kh.matzip.owner.model.dao.RegisterOwnerMapper;
import com.kh.matzip.owner.model.dto.RegisterOwnerDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterOwnerServiceImpl implements RegisterOwnerService {

	private final RegisterOwnerMapper registerOwnerMapper;

    @Override
    public void registerOwner(RegisterOwnerDTO registerOwnerDTO) {

        int userExists = registerOwnerMapper.isOwnerRegister(registerOwnerDTO.getUserNo());
        if (userExists > 0) {
            throw new DuplicateDataException(ResponseCode.DUPLICATIED_OWNER, "이미 사장님 신청을 한 사용자입니다.");
        }

        int storeExists = registerOwnerMapper.isStoreRegister(registerOwnerDTO.getBusinessNo());
        if (storeExists > 0) {
            throw new DuplicateDataException(ResponseCode.DUPLICATIED_STORE, "이미 등록된 사업자 번호입니다.");
        }

        int result = registerOwnerMapper.insertRegisterOwner(registerOwnerDTO);
    }
}

package com.kh.matzip.store.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.global.error.exceptions.StoreAlreadyExistsException;
import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.store.model.dao.StoreMapper;
import com.kh.matzip.store.model.dto.StoreDTO;
import com.kh.matzip.util.file.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j  // Lombok을 사용하여 로그를 추가
public class StoreServiceImpl implements StoreService {

    private final StoreMapper storeMapper;
    private final FileService fileService;

    /**
     * 매장 등록
     * @param storeDto 매장 정보
     * @param images 매장 이미지
     */
    @Override
    @Transactional
    public void insertStore(CustomUserDetails user, StoreDTO storeDto, MultipartFile[] images) {

        Long userNo = user.getUserNo();  // CustomUserDetails에서 userNo를 가져옴
        log.info("매장 등록 시작: 사용자 번호 = {}, 매장 이름 = {}", userNo, storeDto.getStoreName());

        try {
            // 중복 매장 체크
            int count = storeMapper.countStoreByOwnerAndName(userNo, storeDto.getStoreName());
            log.info("중복 매장 체크: 매장 이름 = {}, 중복 여부 = {}", storeDto.getStoreName(), count > 0 ? "중복됨" : "중복 안됨");
            if (count > 0) {
                log.error("중복된 매장 이름: {}", storeDto.getStoreName());
                throw new StoreAlreadyExistsException("이미 등록된 매장입니다.");
            }

            // 매장 등록
            storeMapper.insertStore(storeDto);
            Long storeNo = storeDto.getStoreNo();
            log.info("매장 등록 완료: storeNo = {}", storeNo);

            // 이미지 저장
            if (images == null || images.length == 0) {
                log.error("이미지 파일이 없습니다.");
                throw new IllegalArgumentException("이미지는 최소 1장 이상 등록해야 합니다.");
            }
            log.info("이미지 개수 확인: 등록된 이미지 개수 = {}", images.length);
            if (images.length > 5) {
                log.error("이미지 파일 개수가 5개를 초과했습니다. 등록된 이미지 개수: {}", images.length);
                throw new IllegalArgumentException("이미지는 최대 5장까지 등록할 수 있습니다.");
            }

            // 이미지 저장 처리
            for (MultipartFile image : images) {
                log.info("이미지 파일 처리 시작: 파일 이름 = {}", image.getOriginalFilename());
                if (image.isEmpty()) {
                    log.error("빈 이미지 파일을 업로드하려고 했습니다.");
                    throw new IllegalArgumentException("빈 이미지는 업로드할 수 없습니다.");
                }

                // 파일 경로 저장
                String savedPath = fileService.store(image);
                if (savedPath == null || savedPath.isEmpty()) {
                    log.error("이미지 저장에 실패했습니다. 파일: {}", image.getOriginalFilename());
                    throw new IllegalArgumentException("이미지 저장에 실패했습니다.");
                }
                log.info("이미지 저장 성공: 저장된 경로 = {}", savedPath);
                storeMapper.insertStoreImage(storeNo, savedPath);
            }

            // 편의시설 저장
            if (storeDto.getCategoryConvenience() != null) {
                for (String convenience : storeDto.getCategoryConvenience()) {
                    storeMapper.insertStoreConvenience(storeNo, convenience);
                    log.info("편의시설 저장: {}", convenience);
                }
            }

            // 휴무일 저장
            if (storeDto.getDayOff() != null) {
                for (String offDay : storeDto.getDayOff()) {
                    storeMapper.insertDayOff(storeNo, offDay);
                    log.info("휴무일 저장: {}", offDay);
                }
            }

        } catch (Exception e) {
            log.error("매장 등록 중 예외 발생: ", e);  // 예외 발생 시 로그를 남깁니다.
            throw e;  // 예외를 그대로 던져서 Spring의 기본 에러 처리 메커니즘에 맡깁니다.
        }
    }
}

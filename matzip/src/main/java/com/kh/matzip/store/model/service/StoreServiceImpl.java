package com.kh.matzip.store.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

        Long userNo = 22L; // 임의로 userNo 설정, 실제로는 user.getUserNo()로 설정해야 함
        storeDto.setUserNo(userNo);  // StoreDTO에 userNo 값을 설정

        log.info("매장 등록 시작: 사용자 번호 = {}, 매장 이름 = {}", userNo, storeDto.getStoreName());

        try {
            // Map에 파라미터를 담아서 전달
            Map<String, Object> params = new HashMap<>();
            params.put("userNo", userNo);
            params.put("storeName", storeDto.getStoreName());

            // 중복 매장 체크
            int count = storeMapper.countStoreByOwnerAndName(params); // Map을 전달
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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 최소 1장 이상 등록해야 합니다.");
            }
            log.info("이미지 개수 확인: 등록된 이미지 개수 = {}", images.length);
            if (images.length > 5) {
                log.error("이미지 파일 개수가 5개를 초과했습니다. 등록된 이미지 개수: {}", images.length);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 최대 5장까지 등록할 수 있습니다.");
            }

            // 이미지 저장 처리
            for (MultipartFile image : images) {
                log.debug("이미지 파일 처리 시작: 파일 이름 = {}", image.getOriginalFilename());
                if (image.isEmpty()) {
                    log.error("빈 이미지 파일을 업로드하려고 했습니다.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "빈 이미지는 업로드할 수 없습니다.");
                }

                // 파일 경로 저장
                String savedPath = fileService.store(image);
                if (savedPath == null || savedPath.isEmpty()) {
                    log.error("이미지 저장에 실패했습니다. 파일: {}", image.getOriginalFilename());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패했습니다.");
                }
                log.debug("이미지 저장 성공: 저장된 경로 = {}", savedPath);

                // 이미지 등록을 위한 Map 파라미터 준비
                Map<String, Object> imageParams = new HashMap<>();
                imageParams.put("storeNo", storeNo);
                imageParams.put("image", savedPath);
                storeMapper.insertStoreImage(imageParams); // Map으로 전달
            }

            // 편의시설 저장
            if (storeDto.getCategoryConvenience() != null) {
                for (String convenience : storeDto.getCategoryConvenience()) {
                    // 편의시설 등록을 위한 Map 파라미터 준비
                    Map<String, Object> convenienceParams = new HashMap<>();
                    convenienceParams.put("storeNo", storeNo);
                    convenienceParams.put("convenience", convenience);
                    storeMapper.insertStoreConvenience(convenienceParams); // Map으로 전달
                    log.info("편의시설 저장: {}", convenience);
                }
            }

            // 휴무일 저장
            if (storeDto.getDayOff() != null) {
                for (String offDay : storeDto.getDayOff()) {
                    // 휴무일 등록을 위한 Map 파라미터 준비
                    Map<String, Object> dayOffParams = new HashMap<>();
                    dayOffParams.put("storeNo", storeNo);
                    dayOffParams.put("offDay", offDay);
                    storeMapper.insertDayOff(dayOffParams); // Map으로 전달
                    log.info("휴무일 저장: {}", offDay);
                }
            }

            // 메뉴 저장
            if (storeDto.getMenuList() != null) {
               for (String menuName : storeDto.getMenuList()) {
                Map<String, Object> map = new HashMap<>();
                map.put("storeNo", storeDto.getStoreNo());
                map.put("menuName", menuName);
                storeMapper.insertMenu(map);
            }

            }

        } catch (ResponseStatusException e) {
            log.error("잘못된 요청이 발생했습니다: ", e);
            throw e;  // 클라이언트에 적절한 오류 응답 반환
        } catch (Exception e) {
            log.error("매장 등록 중 예외 발생: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "매장 등록에 실패했습니다.");
        }
    }
}

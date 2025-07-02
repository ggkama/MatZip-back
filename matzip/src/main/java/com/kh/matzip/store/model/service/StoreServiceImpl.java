package com.kh.matzip.store.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

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
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreMapper storeMapper;
    private final FileService fileService;

    @Override
    @Transactional
    public void insertStore(CustomUserDetails user, StoreDTO storeDto, MultipartFile[] images) {
        Long userNo = user.getUserNo();
        storeDto.setUserNo(userNo);

        log.info("매장 등록 시작: 사용자 번호 = {}, 매장 이름 = {}", userNo, storeDto.getStoreName());

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userNo", userNo);
            params.put("storeName", storeDto.getStoreName());

            if (storeMapper.countStoreByOwnerAndName(params) > 0) {
                throw new StoreAlreadyExistsException("이미 등록된 매장입니다.");
            }

            // 1. 매장 등록
            storeMapper.insertStore(storeDto);
            Long storeNo = storeDto.getStoreNo();

            // 2. 이미지 검증 및 저장
            if (images == null || images.length == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 최소 1장 이상 등록해야 합니다.");
            }
            if (images.length > 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 최대 5장까지 등록할 수 있습니다.");
            }

            for (MultipartFile image : images) {
                if (image.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "빈 이미지는 업로드할 수 없습니다.");
                }
                String savedPath = fileService.store(image);
                if (savedPath == null || savedPath.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패했습니다.");
                }
                Map<String, Object> imageMap = new HashMap<>();
                imageMap.put("storeNo", storeNo);
                imageMap.put("image", savedPath);
                storeMapper.insertStoreImage(imageMap);
            }

            // 3. 편의시설 등록
            if (storeDto.getCategoryConvenience() != null) {
                for (String convenience : storeDto.getCategoryConvenience()) {
                    Map<String, Object> convMap = new HashMap<>();
                    convMap.put("storeNo", storeNo);
                    convMap.put("convenience", convenience);
                    storeMapper.insertStoreConvenience(convMap);
                }
            }

            // 4. 정기 휴무 등록
            if (storeDto.getDayOff() != null) {
                for (String offDay : storeDto.getDayOff()) {
                    Map<String, Object> offMap = new HashMap<>();
                    offMap.put("storeNo", storeNo);
                    offMap.put("offDay", offDay);
                    storeMapper.insertDayOff(offMap);
                }
            }

            // 5. 메뉴 등록
            if (storeDto.getMenuList() != null) {
                for (String menuName : storeDto.getMenuList()) {
                    Map<String, Object> menuMap = new HashMap<>();
                    menuMap.put("storeNo", storeNo);
                    menuMap.put("menuName", menuName);
                    storeMapper.insertMenu(menuMap);
                }
            }

            // 6. 임시 휴무 등록
            if (storeDto.getStartDate() != null && storeDto.getEndDate() != null) {
                Map<String, Object> shutdownMap = new HashMap<>();
                shutdownMap.put("storeNo", storeNo);
                shutdownMap.put("startDate", storeDto.getStartDate());
                shutdownMap.put("endDate", storeDto.getEndDate());
                storeMapper.insertShutdownDay(shutdownMap);
            }

            log.info("매장 등록 완료: storeNo = {}", storeNo);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("매장 등록 실패", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "매장 등록에 실패했습니다.");
        }
    }

    @Override
    public boolean existsStoreByUserNo(Long userNo) {
        return storeMapper.existsStoreByUserNo(userNo);
    }

    @Override
    public StoreDTO getStoreByUserNo(Long userNo) {
        StoreDTO store = storeMapper.selectStoreByUserNo(userNo);
        if (store == null) return null;

        Long storeNo = store.getStoreNo();

        store.setCategoryConvenience(storeMapper.selectConveniencesByStoreNo(storeNo));
        store.setDayOff(storeMapper.selectDayOffByStoreNo(storeNo));
        store.setMenuList(storeMapper.selectMenuByStoreNo(storeNo));
        store.setImageList(storeMapper.selectStoreImagesByStoreNo(storeNo));

        Map<String, Object> shutdown = storeMapper.selectShutdownDayByStoreNo(storeNo);
        if (shutdown != null && !shutdown.isEmpty()) {
            store.setStartDate((Date) shutdown.get("START_DATE"));
            store.setEndDate((Date) shutdown.get("END_DATE"));
        }

        return store;
    }

    @Override
    @Transactional
    public void updateStore(
        CustomUserDetails user,
        StoreDTO storeDto,
        MultipartFile[] images,
        List<String> deletedImagePaths,
        List<String> changedOldImages,        
        List<MultipartFile> changedNewImages  
    ) {
        try {
            Long userNo = user.getUserNo();
            storeDto.setUserNo(userNo);

            StoreDTO existingStore = storeMapper.selectStoreByUserNo(userNo);
            if (existingStore == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록된 매장이 없습니다.");
            }

            Long storeNo = existingStore.getStoreNo();
            storeDto.setStoreNo(storeNo);

            storeMapper.updateStore(storeDto);

            // === 편의시설 처리 ===
            List<String> existingConvenience = storeMapper.selectConveniencesByStoreNo(storeNo);
            List<String> newConvenience = storeDto.getCategoryConvenience() != null ? storeDto.getCategoryConvenience() : List.of();

            for (String oldItem : existingConvenience) {
                if (!newConvenience.contains(oldItem)) {
                    storeMapper.deleteSingleConvenience(storeNo, oldItem);
                }
            }
            for (String newItem : newConvenience) {
                if (!existingConvenience.contains(newItem)) {
                    storeMapper.insertStoreConvenience(Map.of("storeNo", storeNo, "convenience", newItem));
                }
            }

            // === 정기 휴무 처리 ===
            List<String> existingDayOff = storeMapper.selectDayOffByStoreNo(storeNo);
            List<String> newDayOff = storeDto.getDayOff() != null ? storeDto.getDayOff() : List.of();

            for (String oldItem : existingDayOff) {
                if (!newDayOff.contains(oldItem)) {
                    storeMapper.deleteSingleDayOff(storeNo, oldItem);
                }
            }
            for (String newItem : newDayOff) {
                if (!existingDayOff.contains(newItem)) {
                    storeMapper.insertDayOff(Map.of("storeNo", storeNo, "offDay", newItem));
                }
            }

            // === 메뉴 처리 ===
            List<String> existingMenus = storeMapper.selectMenuByStoreNo(storeNo);
            List<String> newMenus = storeDto.getMenuList() != null ? storeDto.getMenuList() : List.of();

            for (String oldItem : existingMenus) {
                if (!newMenus.contains(oldItem)) {
                    storeMapper.deleteSingleMenu(storeNo, oldItem);
                }
            }
            for (String newItem : newMenus) {
                if (!existingMenus.contains(newItem)) {
                    storeMapper.insertMenu(Map.of("storeNo", storeNo, "menuName", newItem));
                }
            }

            // === 이미지 처리 ===
            List<String> existingImages = storeMapper.selectStoreImagesByStoreNo(storeNo);
            List<String> toDelete = deletedImagePaths != null ? deletedImagePaths : Collections.emptyList();

            for (String deletedPath : toDelete) {
                String trimmed = deletedPath.trim().replace("\\", "/");
                log.info("삭제 시도: [{}]", trimmed);
                if (existingImages.contains(deletedPath)) {
                    storeMapper.deleteStoreImageByPath(storeNo, deletedPath);
                    fileService.delete(deletedPath);
                }
            }

            int currentCount = existingImages.size() - toDelete.size();
            int newCount = images != null ? images.length : 0;
            int changeCount = changedNewImages != null ? changedNewImages.size() : 0;
            if (currentCount + newCount + changeCount > 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 최대 5장까지 등록할 수 있습니다.");
            }

           // 1. 교체 먼저 처리 (기존 이미지를 유지하면서 바꾸는 작업)
            if (changedOldImages != null && changedNewImages != null) {
                for (int i = 0; i < changedOldImages.size(); i++) {
                    String oldImage = changedOldImages.get(i).trim().replace("\\", "/");
                    MultipartFile newImage = changedNewImages.get(i);

                    if (!newImage.isEmpty()) {
                        fileService.delete(oldImage);
                        String savedPath = fileService.store(newImage);
                        log.info("이미지 교체: {} → {}", oldImage, savedPath);

                        if (savedPath != null && !savedPath.isEmpty()) {
                            storeMapper.updateStoreImage(storeDto.getStoreNo(), oldImage, savedPath);
                        }
                    }
                }
            }

            // === 이미지 삭제 ===
            // List<String> toDelete = deletedImagePaths != null ? deletedImagePaths : Collections.emptyList();
            // for (String deletedPath : toDelete) {
            //     String trimmed = deletedPath.trim().replace("\\", "/");
            //     log.info("삭제 시도: [{}]", trimmed);
            //     storeMapper.deleteStoreImageByPath(storeNo, trimmed);
            //     fileService.delete(trimmed);
            // }

            // === 이미지 추가 ===
            if (images != null) {
                for (MultipartFile image : images) {
                    if (!image.isEmpty()) {
                        String savedPath = fileService.store(image);
                        log.info("저장된 이미지 경로: {}", savedPath);
                        if (savedPath != null && !savedPath.isEmpty()) {
                            storeMapper.insertStoreImage(Map.of("storeNo", storeNo, "image", savedPath));
                        }
                    }
                }
            }

            // 4. 최종 갯수 체크
            int finalCount = storeMapper.selectStoreImagesByStoreNo(storeNo).size();
            if (finalCount > 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 최대 5장까지 등록할 수 있습니다.");
            }

            // === 임시 휴무 처리 ===
            Map<String, Object> shutdown = storeMapper.selectShutdownDayByStoreNo(storeNo);

            if (storeDto.getStartDate() != null && storeDto.getEndDate() != null) {
                if (shutdown != null && shutdown.get("DAY_NO") != null) {
                    Long dayNo = ((BigDecimal) shutdown.get("DAY_NO")).longValue();
                    storeMapper.updateShutdownDay(
                        storeNo,
                        dayNo,
                        new java.sql.Date(storeDto.getStartDate().getTime()),
                        new java.sql.Date(storeDto.getEndDate().getTime())
                    );
                } else {
                    storeMapper.insertShutdownDay(Map.of(
                        "storeNo", storeNo,
                        "startDate", storeDto.getStartDate(),
                        "endDate", storeDto.getEndDate()
                    ));
                }
            }

            log.info("매장 수정 완료: storeNo = {}", storeNo);

        } catch (Exception e) {
            log.error("매장 수정 중 오류 발생", e);
            throw e;
        }
    }

}

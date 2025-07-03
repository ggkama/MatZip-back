package com.kh.matzip.store.model.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.kh.matzip.global.error.exceptions.StoreAlreadyExistsException;
import com.kh.matzip.global.error.exceptions.StoreSaveFailedException;
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
            validateDuplicateStore(userNo, storeDto.getStoreName());

            storeMapper.insertStore(storeDto);
            Long storeNo = storeDto.getStoreNo();

            validateAndSaveImages(storeNo, images);
            updateConveniences(storeNo, storeDto.getCategoryConvenience());
            updateDayOff(storeNo, storeDto.getDayOff());
            updateMenus(storeNo, storeDto.getMenuList());
            updateShutdownPeriod(storeNo, storeDto.getStartDate(), storeDto.getEndDate());

            log.info("매장 등록 완료: storeNo = {}", storeNo);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("매장 등록 실패", e);
            throw new StoreSaveFailedException("매장 등록에 실패했습니다.");
        }
    }

    private void validateDuplicateStore(Long userNo, String storeName) {
        Map<String, Object> params = Map.of(
            "userNo", userNo,
            "storeName", storeName
        );
        if (storeMapper.countStoreByOwnerAndName(params) > 0) {
            throw new StoreAlreadyExistsException("이미 등록된 매장입니다.");
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
    public void updateStore(CustomUserDetails user, StoreDTO storeDto,
                            MultipartFile[] images,
                            List<String> deletedImagePaths,
                            List<String> changedOldImages,
                            List<MultipartFile> changedNewImages) {
        try {
            Long userNo = user.getUserNo();
            storeDto.setUserNo(userNo);

            StoreDTO existingStore = storeMapper.selectStoreByUserNo(userNo);
            if (existingStore == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록된 매장이 없습니다.");
            }

            Long storeNo = existingStore.getStoreNo();
            storeDto.setStoreNo(storeNo);

            // 매장 기본 정보 및 카테고리 관련 수정
            storeMapper.updateStore(storeDto);
            updateConveniences(storeNo, storeDto.getCategoryConvenience());
            updateDayOff(storeNo, storeDto.getDayOff());
            updateMenus(storeNo, storeDto.getMenuList());
            updateShutdownPeriod(storeNo, storeDto.getStartDate(), storeDto.getEndDate());

            // 이미지 처리
            handleImageDeletion(storeNo, deletedImagePaths);
            handleImageReplacement(storeNo, changedOldImages, changedNewImages);
            validateAndSaveImages(storeNo, images);

            // 최종 개수 확인
            int finalCount = storeMapper.selectStoreImagesByStoreNo(storeNo).size();
            if (finalCount > 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 최대 5장까지 등록할 수 있습니다.");
            }

            log.info("매장 수정 완료: storeNo = {}", storeNo);

        } catch (Exception e) {
            log.error("매장 수정 중 오류 발생", e);
            throw e;
        }
    }

    private void handleImageDeletion(Long storeNo, List<String> deletedPaths) {
        if (deletedPaths == null) return;
        for (String path : deletedPaths) {
            String trimmed = path.trim().replace("\\", "/");
            storeMapper.deleteStoreImageByPath(Map.of("storeNo", storeNo, "image", trimmed));
            fileService.delete(trimmed);
        }
    }

    private void handleImageReplacement(Long storeNo, List<String> oldImages, List<MultipartFile> newImages) {
        if (oldImages == null || newImages == null) return;

        for (int i = 0; i < oldImages.size(); i++) {
            String old = oldImages.get(i).trim().replace("\\", "/");
            MultipartFile newImg = newImages.get(i);
            if (!newImg.isEmpty()) {
                fileService.delete(old);
                String newPath = fileService.store(newImg);
                if (newPath != null && !newPath.isEmpty()) {
                    storeMapper.updateStoreImage(Map.of(
                        "storeNo", storeNo,
                        "oldImage", old,
                        "newImage", newPath
                    ));
                }
            }
        }
    }

    private void validateAndSaveImages(Long storeNo, MultipartFile[] images) {
        if (images == null) return;
        for (MultipartFile image : images) {
            if (image.isEmpty()) continue;
            String savedPath = fileService.store(image);
            if (savedPath == null || savedPath.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패했습니다.");
            }
            storeMapper.insertStoreImage(Map.of("storeNo", storeNo, "image", savedPath));
        }
    }

    private void updateConveniences(Long storeNo, List<String> newList) {
        List<String> existing = storeMapper.selectConveniencesByStoreNo(storeNo);
        newList = newList != null ? newList : List.of();

        for (String oldItem : existing) {
            if (!newList.contains(oldItem)) {
                storeMapper.deleteSingleConvenience(Map.of("storeNo", storeNo, "convenience", oldItem));
            }
        }
        for (String newItem : newList) {
            if (!existing.contains(newItem)) {
                storeMapper.insertStoreConvenience(Map.of("storeNo", storeNo, "convenience", newItem));
            }
        }
    }

    private void updateDayOff(Long storeNo, List<String> newList) {
        List<String> existing = storeMapper.selectDayOffByStoreNo(storeNo);
        newList = newList != null ? newList : List.of();

        for (String oldItem : existing) {
            if (!newList.contains(oldItem)) {
                storeMapper.deleteSingleDayOff(Map.of("storeNo", storeNo, "offDay", oldItem));
            }
        }
        for (String newItem : newList) {
            if (!existing.contains(newItem)) {
                storeMapper.insertDayOff(Map.of("storeNo", storeNo, "offDay", newItem));
            }
        }
    }

    private void updateMenus(Long storeNo, List<String> newList) {
        List<String> existing = storeMapper.selectMenuByStoreNo(storeNo);
        newList = newList != null ? newList : List.of();

        for (String oldItem : existing) {
            if (!newList.contains(oldItem)) {
                storeMapper.deleteSingleMenu(Map.of("storeNo", storeNo, "menuName", oldItem));
            }
        }
        for (String newItem : newList) {
            if (!existing.contains(newItem)) {
                storeMapper.insertMenu(Map.of("storeNo", storeNo, "menuName", newItem));
            }
        }
    }

    private void updateShutdownPeriod(Long storeNo, Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return;

        Map<String, Object> shutdown = storeMapper.selectShutdownDayByStoreNo(storeNo);
        if (shutdown != null && shutdown.get("DAY_NO") != null) {
            Long dayNo = ((BigDecimal) shutdown.get("DAY_NO")).longValue();
            storeMapper.updateShutdownDay(Map.of(
                "storeNo", storeNo,
                "dayNo", dayNo,
                "startDate", startDate,
                "endDate", endDate
            ));
        } else {
            storeMapper.insertShutdownDay(Map.of(
                "storeNo", storeNo,
                "startDate", startDate,
                "endDate", endDate
            ));
        }
    }
}

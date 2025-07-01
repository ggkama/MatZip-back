package com.kh.matzip.util.file;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class FileService {
    
    private final Path fileLocation;
    
    public FileService() {
        this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
        log.info("파일 저장 위치: {}", this.fileLocation);  // ← 실제 경로 확인
        try {
            Files.createDirectories(this.fileLocation);
        } catch (IOException e) {
            log.error("업로드 폴더 생성 실패!", e);
            throw new RuntimeException("업로드 폴더 생성 실패!", e);
        }
    }
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int) (Math.random() * 900) + 100;
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String renamedFile = "ep_" + currentTime + "_" + random + ext;
        try {
            Path targetPath = this.fileLocation.resolve(renamedFile);
            log.info("저장된 URL: {}", "/uploads/" + renamedFile);
            file.transferTo(targetPath.toFile());
            log.info("파일 저장 완료: {}", renamedFile);
            return "/uploads/" + renamedFile;
        } catch (IOException e) {
            log.error("파일 저장 실패", e);
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
    
    public void delete(String url) {
        try {
            // URL에서 파일 이름만 추출 (예: "/uploads/ep_202504291105_123.jpg" → "ep_202504291105_123.jpg")
            String filename = Paths.get(url).getFileName().toString();
            Path targetPath = this.fileLocation.resolve(filename);
            Files.deleteIfExists(targetPath);
            log.info("파일 삭제 완료: {}", filename);
        } catch (IOException e) {
            log.error("파일 삭제 실패", e);
            throw new RuntimeException("파일 삭제 실패: " + url, e);
        }
    }
}

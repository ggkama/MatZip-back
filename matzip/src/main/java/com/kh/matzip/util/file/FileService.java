package com.kh.matzip.util.file;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.util.s3.S3Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
	
	private final S3Service s3Service;
	
	@Value("${file.storage}")
	private String storageType;
	
	private final Path fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
	
	@PostConstruct
    public void init() {
        if (storageType.equals("local")) {
            try {
                Files.createDirectories(this.fileLocation);
                log.info("로컬 파일 저장 위치: {}", this.fileLocation);
            } catch (IOException e) {
                log.error("로컬 업로드 폴더 생성 실패!", e);
                throw new RuntimeException("로컬 업로드 폴더 생성 실패!", e);
            }
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
	    
	    if (storageType.equals("s3")) {
	    	log.info("S3에 파일 업로드 중 입니다.");
	    	return s3Service.uploadFile(file);
	    } else {

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
	}
	
	public void delete(String url) {
		
		if (storageType.equals("s3")) {
            log.info("S3 파일 삭제 중: {}", url);
            s3Service.deleteFile(url);
        } else {
        	
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

}

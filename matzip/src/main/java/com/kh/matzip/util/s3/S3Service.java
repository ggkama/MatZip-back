package com.kh.matzip.util.s3;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client; 
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Client s3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	
	@Value("${cloud.aws.region.static}")
    private String region;
	
	/* s3 파일 업로드 */
	public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 실패", e);
        }

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }
	
	
	public void deleteFile(String fileUrl) {
        try {
            String key;

            if (fileUrl.startsWith("http")) {
                URL url = new URL(fileUrl);
                key = url.getPath().substring(1);
            } else {
                key = fileUrl.startsWith("/") ? fileUrl.substring(1) : fileUrl;
            }

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteRequest);
        } catch (Exception e) {
            throw new RuntimeException("S3 파일 삭제 실패", e);
        }
    }
}

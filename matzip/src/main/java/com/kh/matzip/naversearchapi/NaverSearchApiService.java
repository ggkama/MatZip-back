package com.kh.matzip.naversearchapi;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class NaverSearchApiService {
    
    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchBlog(String query) {
        try {
                    System.out.println("네이버 검색 storeName: " + query);

            String url = "https://openapi.naver.com/v1/search/blog.json?query=" + URLEncoder.encode(query, "UTF-8") + "&display=5&sort=sim";

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
            );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

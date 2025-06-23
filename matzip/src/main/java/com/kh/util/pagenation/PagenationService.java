package com.kh.util.pagenation;

import org.springframework.stereotype.Service;

@Service
public class PagenationService {
    
    public int getStartIndex( int pageNo, int size){
        return pageNo * size;
    }
}

package com.yanzhiyu.mylovephotos.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotoDTO {
    
    private String id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String thumbnailUrl;
    private String originalUrl;
    private String folderId;
    private LocalDateTime uploadTime;
}

package com.yanzhiyu.mylovephotos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    
    private String id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String thumbnailUrl;
    private String originalUrl;
    private String folderId;
    private LocalDateTime uploadTime;
    
    public Photo(String id, String fileName, String filePath, Long fileSize, 
                String thumbnailUrl, String originalUrl, String folderId) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.thumbnailUrl = thumbnailUrl;
        this.originalUrl = originalUrl;
        this.folderId = folderId;
        this.uploadTime = LocalDateTime.now();
    }
}

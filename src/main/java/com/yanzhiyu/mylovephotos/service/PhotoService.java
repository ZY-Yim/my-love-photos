package com.yanzhiyu.mylovephotos.service;

import com.yanzhiyu.mylovephotos.dto.PhotoDTO;
import com.yanzhiyu.mylovephotos.entity.Photo;
import com.yanzhiyu.mylovephotos.entity.Folder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PhotoService {
    
    @Autowired
    private StorageService storageService;
    
    @Autowired
    private OssService ossService;
    
    /**
     * 上传照片
     */
    public PhotoDTO uploadPhoto(MultipartFile file, String folderId) throws IOException {
        // 验证文件夹权限
        String folderPath = "photos";
        
        if (folderId != null && !folderId.isEmpty()) {
            Folder folder = storageService.getFolderById(folderId)
                    .orElseThrow(() -> new RuntimeException("文件夹不存在"));
            folderPath = "photos" + folder.getPath();
        }
        
        // 上传到OSS
        String originalUrl = ossService.uploadFile(file, folderPath);
        String thumbnailUrl = ossService.generateThumbnail(originalUrl);
        
        // 保存到内存
        Photo photo = new Photo();
        photo.setFileName(file.getOriginalFilename());
        photo.setFilePath(originalUrl);
        photo.setFileSize(file.getSize());
        photo.setThumbnailUrl(thumbnailUrl);
        photo.setOriginalUrl(originalUrl);
        photo.setFolderId(folderId);
        
        photo = storageService.savePhoto(photo);
        
        return convertToDTO(photo);
    }
    
    /**
     * 获取照片列表
     */
    public List<PhotoDTO> getPhotos(String folderId) {
        List<Photo> photos;
        
        if (folderId == null || folderId.isEmpty()) {
            photos = storageService.getRootPhotos();
        } else {
            // 验证文件夹是否存在
            storageService.getFolderById(folderId)
                    .orElseThrow(() -> new RuntimeException("文件夹不存在"));
            
            photos = storageService.getPhotosByFolderId(folderId);
        }
        
        return photos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 删除照片
     */
    public void deletePhoto(String photoId) {
        Photo photo = storageService.getPhotoById(photoId)
                .orElseThrow(() -> new RuntimeException("照片不存在"));
        
        // 从OSS删除文件
        ossService.deleteFile(photo.getOriginalUrl());
        
        // 从内存删除记录
        storageService.deletePhoto(photoId);
    }
    
    private PhotoDTO convertToDTO(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setFileName(photo.getFileName());
        dto.setFilePath(photo.getFilePath());
        dto.setFileSize(photo.getFileSize());
        dto.setThumbnailUrl(photo.getThumbnailUrl());
        dto.setOriginalUrl(photo.getOriginalUrl());
        dto.setFolderId(photo.getFolderId());
        dto.setUploadTime(photo.getUploadTime());
        return dto;
    }
}

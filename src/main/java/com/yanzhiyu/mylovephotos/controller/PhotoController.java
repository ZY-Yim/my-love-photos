package com.yanzhiyu.mylovephotos.controller;

import com.yanzhiyu.mylovephotos.common.ApiResponse;
import com.yanzhiyu.mylovephotos.dto.PhotoDTO;
import com.yanzhiyu.mylovephotos.service.PhotoService;
import com.yanzhiyu.mylovephotos.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PhotoController {
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * 上传照片
     */
    @PostMapping("/upload")
    public ApiResponse<PhotoDTO> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderId", required = false) String folderId,
            HttpServletRequest request) {
        try {
            validateToken(request);
            
            if (file.isEmpty()) {
                return ApiResponse.error("请选择要上传的文件");
            }
            
            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ApiResponse.error("只支持上传图片文件");
            }
            
            PhotoDTO photo = photoService.uploadPhoto(file, folderId);
            return ApiResponse.success("上传成功", photo);
        } catch (Exception e) {
            log.error("上传照片失败: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        }
    }
    
    /**
     * 获取照片列表
     */
    @GetMapping("/photos")
    public ApiResponse<List<PhotoDTO>> getPhotos(
            @RequestParam(value = "folderId", required = false) String folderId,
            HttpServletRequest request) {
        try {
            validateToken(request);
            List<PhotoDTO> photos = photoService.getPhotos(folderId);
            return ApiResponse.success(photos);
        } catch (Exception e) {
            log.error("获取照片列表失败: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        }
    }
    
    /**
     * 删除照片
     */
    @DeleteMapping("/photos/{photoId}")
    public ApiResponse<Void> deletePhoto(
            @PathVariable String photoId,
            HttpServletRequest request) {
        try {
            validateToken(request);
            photoService.deletePhoto(photoId);
            return ApiResponse.success("删除成功", null);
        } catch (Exception e) {
            log.error("删除照片失败: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        }
    }
    
    private void validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("未登录或token无效");
        }
        
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("token无效");
        }
    }
}

package com.yanzhiyu.mylovephotos.controller;

import com.yanzhiyu.mylovephotos.common.ApiResponse;
import com.yanzhiyu.mylovephotos.dto.CreateFolderRequest;
import com.yanzhiyu.mylovephotos.dto.FolderDTO;
import com.yanzhiyu.mylovephotos.service.FolderService;
import com.yanzhiyu.mylovephotos.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/folders")
@CrossOrigin(origins = "*")
public class FolderController {
    
    @Autowired
    private FolderService folderService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * 获取文件夹列表
     */
    @GetMapping
    public ApiResponse<List<FolderDTO>> getFolders(HttpServletRequest request) {
        try {
            validateToken(request);
            List<FolderDTO> folders = folderService.getFolderTree();
            return ApiResponse.success(folders);
        } catch (Exception e) {
            log.error("获取文件夹列表失败: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        }
    }
    
    /**
     * 创建文件夹
     */
    @PostMapping
    public ApiResponse<FolderDTO> createFolder(
            @Valid @RequestBody CreateFolderRequest request,
            HttpServletRequest httpRequest) {
        try {
            validateToken(httpRequest);
            FolderDTO folder = folderService.createFolder(request);
            return ApiResponse.success("文件夹创建成功", folder);
        } catch (Exception e) {
            log.error("创建文件夹失败: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        }
    }
    
    /**
     * 删除文件夹
     */
    @DeleteMapping("/{folderId}")
    public ApiResponse<Void> deleteFolder(
            @PathVariable String folderId,
            HttpServletRequest request) {
        try {
            validateToken(request);
            folderService.deleteFolder(folderId);
            return ApiResponse.success("文件夹删除成功", null);
        } catch (Exception e) {
            log.error("删除文件夹失败: {}", e.getMessage());
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

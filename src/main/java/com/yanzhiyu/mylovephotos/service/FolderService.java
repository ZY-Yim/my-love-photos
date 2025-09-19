package com.yanzhiyu.mylovephotos.service;

import com.yanzhiyu.mylovephotos.dto.CreateFolderRequest;
import com.yanzhiyu.mylovephotos.dto.FolderDTO;
import com.yanzhiyu.mylovephotos.entity.Folder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FolderService {
    
    @Autowired
    private StorageService storageService;
    
    /**
     * 获取用户的文件夹树结构
     */
    public List<FolderDTO> getFolderTree() {
        List<Folder> rootFolders = storageService.getRootFolders();
        return buildFolderTree(rootFolders);
    }
    
    /**
     * 创建文件夹
     */
    public FolderDTO createFolder(CreateFolderRequest request) {
        String parentId = null;
        String parentPath = "";
        
        if (request.getParentId() != null && !request.getParentId().isEmpty()) {
            parentId = request.getParentId();
            Folder parentFolder = storageService.getFolderById(parentId)
                    .orElseThrow(() -> new RuntimeException("父文件夹不存在"));
            parentPath = parentFolder.getPath();
        }
        
        // 检查同级目录下是否已存在同名文件夹
        if (storageService.existsFolderByNameAndParentId(request.getName(), parentId)) {
            throw new RuntimeException("文件夹名称已存在");
        }
        
        Folder folder = new Folder();
        folder.setName(request.getName());
        folder.setPath(parentPath.isEmpty() ? request.getName() : parentPath + "/" + request.getName());
        folder.setParentId(parentId);
        
        folder = storageService.saveFolder(folder);
        
        return convertToDTO(folder);
    }
    
    /**
     * 删除文件夹
     */
    public void deleteFolder(String folderId) {
        Folder folder = storageService.getFolderById(folderId)
                .orElseThrow(() -> new RuntimeException("文件夹不存在"));
        
        storageService.deleteFolder(folderId);
    }
    
    private List<FolderDTO> buildFolderTree(List<Folder> folders) {
        return folders.stream().map(folder -> {
            FolderDTO dto = convertToDTO(folder);
            List<Folder> children = storageService.getChildFolders(folder.getId());
            dto.setChildren(buildFolderTree(children));
            return dto;
        }).collect(Collectors.toList());
    }
    
    private FolderDTO convertToDTO(Folder folder) {
        FolderDTO dto = new FolderDTO();
        dto.setId(folder.getId());
        dto.setName(folder.getName());
        dto.setPath(folder.getPath());
        dto.setParentId(folder.getParentId());
        dto.setCreateTime(folder.getCreateTime());
        dto.setChildren(new ArrayList<>());
        return dto;
    }
}

package com.yanzhiyu.mylovephotos.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FolderDTO {
    
    private String id;
    private String name;
    private String path;
    private String parentId;
    private List<FolderDTO> children;
    private LocalDateTime createTime;
}

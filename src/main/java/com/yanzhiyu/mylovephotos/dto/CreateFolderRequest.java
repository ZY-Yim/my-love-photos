package com.yanzhiyu.mylovephotos.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateFolderRequest {
    
    @NotBlank(message = "文件夹名称不能为空")
    private String name;
    
    private String parentId;
}

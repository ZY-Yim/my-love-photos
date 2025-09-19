package com.yanzhiyu.mylovephotos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    
    private String id;
    private String name;
    private String path;
    private String parentId;
    private LocalDateTime createTime;
    private List<Folder> children = new ArrayList<>();
    
    public Folder(String id, String name, String path, String parentId) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.parentId = parentId;
        this.createTime = LocalDateTime.now();
        this.children = new ArrayList<>();
    }
}

package com.yanzhiyu.mylovephotos.service;

import com.yanzhiyu.mylovephotos.entity.Folder;
import com.yanzhiyu.mylovephotos.entity.Photo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StorageService {
    
    private final Map<String, Folder> folders = new ConcurrentHashMap<>();
    private final Map<String, Photo> photos = new ConcurrentHashMap<>();
    private final AtomicLong folderIdGenerator = new AtomicLong(1);
    private final AtomicLong photoIdGenerator = new AtomicLong(1);
    
    // 文件夹操作
    public List<Folder> getAllFolders() {
        return new ArrayList<>(folders.values());
    }
    
    public List<Folder> getRootFolders() {
        return folders.values().stream()
                .filter(folder -> folder.getParentId() == null)
                .sorted(Comparator.comparing(Folder::getName))
                .toList();
    }
    
    public List<Folder> getChildFolders(String parentId) {
        return folders.values().stream()
                .filter(folder -> Objects.equals(folder.getParentId(), parentId))
                .sorted(Comparator.comparing(Folder::getName))
                .toList();
    }
    
    public Optional<Folder> getFolderById(String id) {
        return Optional.ofNullable(folders.get(id));
    }
    
    public Folder saveFolder(Folder folder) {
        if (folder.getId() == null) {
            folder.setId(String.valueOf(folderIdGenerator.getAndIncrement()));
        }
        folders.put(folder.getId(), folder);
        return folder;
    }
    
    public void deleteFolder(String id) {
        folders.remove(id);
        // 递归删除子文件夹
        getChildFolders(id).forEach(child -> deleteFolder(child.getId()));
        // 删除该文件夹下的所有照片
        getPhotosByFolderId(id).forEach(photo -> deletePhoto(photo.getId()));
    }
    
    public boolean existsFolderByNameAndParentId(String name, String parentId) {
        return folders.values().stream()
                .anyMatch(folder -> folder.getName().equals(name) && 
                         Objects.equals(folder.getParentId(), parentId));
    }
    
    // 照片操作
    public List<Photo> getAllPhotos() {
        return new ArrayList<>(photos.values());
    }
    
    public List<Photo> getPhotosByFolderId(String folderId) {
        return photos.values().stream()
                .filter(photo -> Objects.equals(photo.getFolderId(), folderId))
                .sorted(Comparator.comparing(Photo::getUploadTime).reversed())
                .toList();
    }
    
    public List<Photo> getRootPhotos() {
        return photos.values().stream()
                .filter(photo -> photo.getFolderId() == null)
                .sorted(Comparator.comparing(Photo::getUploadTime).reversed())
                .toList();
    }
    
    public Optional<Photo> getPhotoById(String id) {
        return Optional.ofNullable(photos.get(id));
    }
    
    public Photo savePhoto(Photo photo) {
        if (photo.getId() == null) {
            photo.setId(String.valueOf(photoIdGenerator.getAndIncrement()));
        }
        photos.put(photo.getId(), photo);
        return photo;
    }
    
    public void deletePhoto(String id) {
        photos.remove(id);
    }
}

package com.yanzhiyu.mylovephotos.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.yanzhiyu.mylovephotos.config.OssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class OssService {
    
    @Autowired
    private OSS ossClient;
    
    @Autowired
    private OssProperties ossProperties;
    
    /**
     * 上传文件到OSS
     */
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + extension;
        String objectKey = folder + "/" + fileName;
        
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                ossProperties.getBucketName(),
                objectKey,
                inputStream,
                metadata
            );
            
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            log.info("文件上传成功: {}", objectKey);
            
            return ossProperties.getBaseUrl() + "/" + objectKey;
        } catch (Exception e) {
            log.error("文件上传失败: ", e);
            throw new IOException("文件上传失败", e);
        }
    }
    
    /**
     * 删除OSS文件
     */
    public void deleteFile(String fileUrl) {
        try {
            String objectKey = fileUrl.replace(ossProperties.getBaseUrl() + "/", "");
            ossClient.deleteObject(ossProperties.getBucketName(), objectKey);
            log.info("文件删除成功: {}", objectKey);
        } catch (Exception e) {
            log.error("文件删除失败: ", e);
        }
    }
    
    /**
     * 生成缩略图（这里简化处理，实际应用中可以使用OSS的图片处理功能）
     */
    public String generateThumbnail(String originalUrl) {
        // 使用阿里云OSS的图片处理功能生成缩略图
        return originalUrl + "?x-oss-process=image/resize,m_fill,w_200,h_200";
    }
}

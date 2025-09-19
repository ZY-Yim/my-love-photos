import { uploadPhoto } from './api';

// 上传文件到后端
export interface UploadOptions {
  file: File;
  folderId?: string;
  onProgress?: (percent: number) => void;
}

export const uploadFile = async ({ file, folderId, onProgress }: UploadOptions): Promise<any> => {
  try {
    const formData = new FormData();
    formData.append('file', file);
    if (folderId) {
      formData.append('folderId', folderId);
    }

    // 模拟进度更新（实际上axios可以监听上传进度）
    if (onProgress) {
      onProgress(0);
      // 这里可以配置axios的onUploadProgress来实现真实的进度监听
    }

    const result = await uploadPhoto(formData);
    
    if (onProgress) {
      onProgress(100);
    }

    return result;
  } catch (error) {
    console.error('Upload failed:', error);
    throw error;
  }
};

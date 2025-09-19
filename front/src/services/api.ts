import api from '../utils/request';
import { API_ENDPOINTS } from '../config/constants';

// 用户登录
export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userInfo: {
    id: string;
    username: string;
    nickname?: string;
  };
}

export const login = (data: LoginRequest): Promise<LoginResponse> => {
  return api.post(API_ENDPOINTS.LOGIN, data);
};

// 上传照片到后端
export const uploadPhoto = (formData: FormData): Promise<any> => {
  return api.post(API_ENDPOINTS.UPLOAD, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

// 获取文件夹列表
export interface Folder {
  id: string;
  name: string;
  path: string;
  parentId?: string;
  children?: Folder[];
}

export const getFolders = (): Promise<Folder[]> => {
  return api.get(API_ENDPOINTS.FOLDERS);
};

// 创建文件夹
export interface CreateFolderRequest {
  name: string;
  parentId?: string;
}

export const createFolder = (data: CreateFolderRequest): Promise<Folder> => {
  return api.post(API_ENDPOINTS.FOLDERS, data);
};

// 获取照片列表
export interface Photo {
  id: string;
  fileName: string;
  filePath: string;
  fileSize: number;
  thumbnailUrl: string;
  originalUrl: string;
  uploadTime: string;
}

export const getPhotos = (folderId?: string): Promise<Photo[]> => {
  return api.get(API_ENDPOINTS.PHOTOS, { params: { folderId } });
};

// 删除照片
export const deletePhoto = (photoId: string): Promise<void> => {
  return api.delete(`${API_ENDPOINTS.PHOTO_DELETE}/${photoId}`);
};

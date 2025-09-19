// API基础配置
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

// API接口配置
export const API_ENDPOINTS = {
  LOGIN: '/api/login',
  UPLOAD: '/api/upload',
  FOLDERS: '/api/folders',
  PHOTOS: '/api/photos',
  PHOTO_DELETE: '/api/photos'
};

// 本地存储键名
export const STORAGE_KEYS = {
  TOKEN: 'auth_token',
  USER_INFO: 'user_info'
};

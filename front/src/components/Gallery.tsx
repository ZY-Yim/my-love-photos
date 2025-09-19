import React, { useState, useEffect } from 'react';
import { Layout, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import Header from './Header';
import Sidebar from './Sidebar';
import PhotoGrid from './PhotoGrid';
import UploadModal from './UploadModal';
import { getFolders, getPhotos } from '../services/api';
import type { Folder, Photo } from '../services/api';
import { STORAGE_KEYS } from '../config/constants';
import './Gallery.css';

const { Content } = Layout;

const Gallery: React.FC = () => {
  const [folders, setFolders] = useState<Folder[]>([]);
  const [photos, setPhotos] = useState<Photo[]>([]);
  const [currentFolder, setCurrentFolder] = useState<Folder | null>(null);
  const [uploadModalVisible, setUploadModalVisible] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // 检查登录状态
  useEffect(() => {
    const token = localStorage.getItem(STORAGE_KEYS.TOKEN);
    if (!token) {
      navigate('/login');
      return;
    }
    
    loadFolders();
    loadPhotos();
  }, [navigate]);

  // 加载文件夹列表
  const loadFolders = async () => {
    try {
      const folderList = await getFolders();
      setFolders(folderList);
    } catch (error: any) {
      message.error('加载文件夹失败');
    }
  };

  // 加载照片列表
  const loadPhotos = async (folderId?: string) => {
    try {
      setLoading(true);
      const photoList = await getPhotos(folderId);
      setPhotos(photoList);
    } catch (error: any) {
      message.error('加载照片失败');
    } finally {
      setLoading(false);
    }
  };

  // 切换文件夹
  const handleFolderChange = (folder: Folder | null) => {
    setCurrentFolder(folder);
    loadPhotos(folder?.id);
  };

  // 上传完成后刷新照片列表
  const handleUploadSuccess = () => {
    loadPhotos(currentFolder?.id);
    setUploadModalVisible(false);
  };

  // 创建文件夹后刷新文件夹列表
  const handleFolderCreated = () => {
    loadFolders();
  };

  return (
    <Layout className="gallery-layout">
      <Header 
        onUpload={() => setUploadModalVisible(true)}
      />
      <Layout>
        <Sidebar 
          folders={folders}
          currentFolder={currentFolder}
          onFolderChange={handleFolderChange}
          onFolderCreated={handleFolderCreated}
        />
        <Content className="gallery-content">
          <PhotoGrid 
            photos={photos}
            loading={loading}
            onPhotoDeleted={() => loadPhotos(currentFolder?.id)}
          />
        </Content>
      </Layout>

      <UploadModal
        visible={uploadModalVisible}
        folderId={currentFolder?.id}
        onCancel={() => setUploadModalVisible(false)}
        onSuccess={handleUploadSuccess}
      />
    </Layout>
  );
};

export default Gallery;

import React, { useState } from 'react';
import { Row, Col, Card, Image, Modal, Button, message, Spin } from 'antd';
import { DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { deletePhoto } from '../services/api';
import type { Photo } from '../services/api';
import './PhotoGrid.css';

const { Meta } = Card;

interface PhotoGridProps {
  photos: Photo[];
  loading: boolean;
  onPhotoDeleted: () => void;
}

const PhotoGrid: React.FC<PhotoGridProps> = ({ photos, loading, onPhotoDeleted }) => {
  const [previewVisible, setPreviewVisible] = useState(false);
  const [previewImage, setPreviewImage] = useState('');
  const [previewTitle, setPreviewTitle] = useState('');

  // 预览图片
  const handlePreview = (photo: Photo) => {
    setPreviewImage(photo.originalUrl);
    setPreviewTitle(photo.fileName);
    setPreviewVisible(true);
  };

  // 删除图片
  const handleDelete = (photo: Photo) => {
    Modal.confirm({
      title: '确认删除',
      content: `确定要删除图片 "${photo.fileName}" 吗？`,
      okText: '删除',
      okType: 'danger',
      cancelText: '取消',
      onOk: async () => {
        try {
          await deletePhoto(photo.id);
          message.success('删除成功');
          onPhotoDeleted();
        } catch (error: any) {
          message.error('删除失败');
        }
      },
    });
  };

  if (loading) {
    return (
      <div className="photo-grid-loading">
        <Spin size="large" />
      </div>
    );
  }

  if (photos.length === 0) {
    return (
      <div className="photo-grid-empty">
        <p>暂无图片</p>
      </div>
    );
  }

  return (
    <div className="photo-grid">
      <Row gutter={[16, 16]}>
        {photos.map((photo) => (
          <Col xs={12} sm={8} md={6} lg={4} xl={3} key={photo.id}>
            <Card
              hoverable
              className="photo-card"
              cover={
                <div className="photo-cover">
                  <img
                    alt={photo.fileName}
                    src={photo.thumbnailUrl}
                    className="photo-thumbnail"
                  />
                  <div className="photo-overlay">
                    <Button
                      type="text"
                      icon={<EyeOutlined />}
                      onClick={() => handlePreview(photo)}
                      className="overlay-button"
                    />
                    <Button
                      type="text"
                      icon={<DeleteOutlined />}
                      onClick={() => handleDelete(photo)}
                      className="overlay-button delete-button"
                    />
                  </div>
                </div>
              }
            >
              <Meta
                title={photo.fileName}
                description={`${(photo.fileSize / 1024 / 1024).toFixed(2)} MB`}
              />
            </Card>
          </Col>
        ))}
      </Row>

      <Modal
        open={previewVisible}
        title={previewTitle}
        footer={null}
        onCancel={() => setPreviewVisible(false)}
        width="80%"
        centered
      >
        <Image
          alt={previewTitle}
          style={{ width: '100%' }}
          src={previewImage}
          preview={false}
        />
      </Modal>
    </div>
  );
};

export default PhotoGrid;

import React, { useState } from 'react';
import { Modal, Upload, Button, Progress, message } from 'antd';
import { InboxOutlined } from '@ant-design/icons';
import { uploadFile } from '../services/upload';

const { Dragger } = Upload;

interface UploadModalProps {
  visible: boolean;
  folderId?: string;
  onCancel: () => void;
  onSuccess: () => void;
}

const UploadModal: React.FC<UploadModalProps> = ({
  visible,
  folderId,
  onCancel,
  onSuccess,
}) => {
  const [fileList, setFileList] = useState<any[]>([]);
  const [uploading, setUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState(0);

  // 处理文件上传
  const handleUpload = async () => {
    if (fileList.length === 0) {
      message.warning('请选择要上传的文件');
      return;
    }

    setUploading(true);
    setUploadProgress(0);

    try {
      for (let i = 0; i < fileList.length; i++) {
        const file = fileList[i].originFileObj;
        
        await uploadFile({
          file,
          folderId,
          onProgress: (percent) => {
            const totalProgress = ((i / fileList.length) * 100) + (percent / fileList.length);
            setUploadProgress(Math.round(totalProgress));
          },
        });
      }

      message.success('上传成功！');
      setFileList([]);
      setUploadProgress(0);
      onSuccess();
    } catch (error: any) {
      message.error('上传失败：' + (error.message || '未知错误'));
    } finally {
      setUploading(false);
    }
  };

  // 处理取消
  const handleCancel = () => {
    if (!uploading) {
      setFileList([]);
      setUploadProgress(0);
      onCancel();
    }
  };

  const uploadProps = {
    multiple: true,
    fileList,
    beforeUpload: (file: File) => {
      // 检查文件类型
      const isImage = file.type.startsWith('image/');
      if (!isImage) {
        message.error('只能上传图片文件！');
        return false;
      }

      // 检查文件大小（限制为50MB）
      const isLt50M = file.size / 1024 / 1024 < 50;
      if (!isLt50M) {
        message.error('图片大小不能超过50MB！');
        return false;
      }

      return false; // 阻止自动上传
    },
    onChange: (info: any) => {
      setFileList(info.fileList);
    },
    onRemove: (file: any) => {
      const index = fileList.indexOf(file);
      const newFileList = fileList.slice();
      newFileList.splice(index, 1);
      setFileList(newFileList);
    },
  };

  return (
    <Modal
      title="上传图片"
      open={visible}
      onCancel={handleCancel}
      footer={[
        <Button key="cancel" onClick={handleCancel} disabled={uploading}>
          取消
        </Button>,
        <Button
          key="upload"
          type="primary"
          loading={uploading}
          onClick={handleUpload}
          disabled={fileList.length === 0}
        >
          {uploading ? `上传中 ${uploadProgress}%` : '开始上传'}
        </Button>,
      ]}
      width={600}
      destroyOnClose
    >
      <div style={{ marginBottom: 16 }}>
        <Dragger {...uploadProps}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">点击或拖拽文件到此区域上传</p>
          <p className="ant-upload-hint">
            支持单个或批量上传。只支持图片格式，单个文件不超过50MB。
          </p>
        </Dragger>
      </div>

      {uploading && (
        <div style={{ marginTop: 16 }}>
          <Progress 
            percent={uploadProgress} 
            status={uploadProgress === 100 ? 'success' : 'active'}
          />
        </div>
      )}
    </Modal>
  );
};

export default UploadModal;

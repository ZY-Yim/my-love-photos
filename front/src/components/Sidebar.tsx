import React, { useState } from 'react';
import { Layout, Tree, Button, Modal, Form, Input, message } from 'antd';
import { FolderOutlined, PlusOutlined } from '@ant-design/icons';
import { createFolder } from '../services/api';
import type { Folder } from '../services/api';

const { Sider } = Layout;

interface SidebarProps {
  folders: Folder[];
  currentFolder: Folder | null;
  onFolderChange: (folder: Folder | null) => void;
  onFolderCreated: () => void;
}

const Sidebar: React.FC<SidebarProps> = ({
  folders,
  currentFolder,
  onFolderChange,
  onFolderCreated,
}) => {
  const [createModalVisible, setCreateModalVisible] = useState(false);
  const [form] = Form.useForm();

  // 构建树形数据
  const buildTreeData = (folders: Folder[]) => {
    const treeData = [
      {
        title: '全部照片',
        key: 'all',
        icon: <FolderOutlined />,
      },
    ];

    const folderNodes = folders.map(folder => ({
      title: folder.name,
      key: folder.id,
      icon: <FolderOutlined />,
      folder: folder,
    }));

    return [...treeData, ...folderNodes];
  };

  // 选择文件夹
  const handleSelect = (selectedKeys: React.Key[]) => {
    const key = selectedKeys[0];
    if (key === 'all') {
      onFolderChange(null);
    } else {
      const folder = folders.find(f => f.id === key);
      onFolderChange(folder || null);
    }
  };

  // 创建文件夹
  const handleCreateFolder = async (values: { name: string }) => {
    try {
      await createFolder({
        name: values.name,
        parentId: currentFolder?.id,
      });
      
      message.success('文件夹创建成功');
      setCreateModalVisible(false);
      form.resetFields();
      onFolderCreated();
    } catch (error: any) {
      message.error('创建文件夹失败');
    }
  };

  const treeData = buildTreeData(folders);
  const selectedKeys = currentFolder ? [currentFolder.id] : ['all'];

  return (
    <Sider width={250} className="gallery-sidebar">
      <div className="sidebar-header">
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          block
          onClick={() => setCreateModalVisible(true)}
        >
          新建文件夹
        </Button>
      </div>
      
      <div className="sidebar-content">
        <Tree
          showIcon
          selectedKeys={selectedKeys}
          treeData={treeData}
          onSelect={handleSelect}
        />
      </div>

      <Modal
        title="新建文件夹"
        open={createModalVisible}
        onCancel={() => {
          setCreateModalVisible(false);
          form.resetFields();
        }}
        onOk={() => form.submit()}
      >
        <Form
          form={form}
          onFinish={handleCreateFolder}
          layout="vertical"
        >
          <Form.Item
            name="name"
            label="文件夹名称"
            rules={[
              { required: true, message: '请输入文件夹名称' },
              { max: 50, message: '文件夹名称不能超过50个字符' },
            ]}
          >
            <Input placeholder="请输入文件夹名称" />
          </Form.Item>
        </Form>
      </Modal>
    </Sider>
  );
};

export default Sidebar;

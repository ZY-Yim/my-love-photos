import React from 'react';
import { Layout, Button, Dropdown, Avatar, Space } from 'antd';
import { UploadOutlined, UserOutlined, LogoutOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { STORAGE_KEYS } from '../config/constants';

const { Header: AntHeader } = Layout;

interface HeaderProps {
  onUpload: () => void;
}

const Header: React.FC<HeaderProps> = ({ onUpload }) => {
  const navigate = useNavigate();

  // 获取用户信息
  const userInfoStr = localStorage.getItem(STORAGE_KEYS.USER_INFO);
  const userInfo = userInfoStr ? JSON.parse(userInfoStr) : null;

  // 退出登录
  const handleLogout = () => {
    localStorage.removeItem(STORAGE_KEYS.TOKEN);
    localStorage.removeItem(STORAGE_KEYS.USER_INFO);
    navigate('/login');
  };

  const userMenuItems = [
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      onClick: handleLogout,
    },
  ];

  return (
    <AntHeader className="gallery-header">
      <div className="header-content">
        <div className="header-left">
          <h1 className="header-title">我的相册</h1>
        </div>
        
        <div className="header-right">
          <Space size="middle">
            <Button 
              type="primary" 
              icon={<UploadOutlined />}
              onClick={onUpload}
            >
              上传图片
            </Button>
            
            <Dropdown 
              menu={{ items: userMenuItems }}
              placement="bottomRight"
            >
              <div className="user-info">
                <Avatar icon={<UserOutlined />} />
                <span className="username">
                  {userInfo?.nickname || userInfo?.username || '用户'}
                </span>
              </div>
            </Dropdown>
          </Space>
        </div>
      </div>
    </AntHeader>
  );
};

export default Header;

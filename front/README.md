# 我的相册 - 前端项目

这是一个基于React + TypeScript + Vite构建的相册管理前端应用。

## 功能特性

### 1. 登录页面
- 用户名/密码登录
- JWT Token认证
- 自动跳转和路由守卫

### 2. 主界面 - 相册管理器
- **顶部导航栏**: 显示用户信息、上传按钮、退出功能
- **左侧侧边栏**: 文件夹树形结构，支持新建文件夹
- **主体内容区**: 照片网格展示，支持预览和删除

### 3. 上传功能
- 拖拽上传支持
- 多文件批量上传
- 实时上传进度显示
- 文件类型和大小验证

### 4. 照片管理
- 缩略图网格展示
- 大图预览模式
- 照片删除功能
- 按文件夹分类管理

## 技术栈

- **框架**: React 18 + TypeScript
- **构建工具**: Vite
- **UI组件库**: Ant Design
- **路由**: React Router v6
- **HTTP客户端**: Axios
- **样式**: CSS + Ant Design

## API接口

所有API请求都发送到后端服务器（默认: http://127.0.0.1:8080）

### 主要接口
- `POST /api/login` - 用户登录
- `GET /api/folders` - 获取文件夹列表
- `POST /api/folders` - 创建文件夹
- `GET /api/photos` - 获取照片列表
- `POST /api/upload` - 上传照片
- `DELETE /api/photos/:id` - 删除照片

## 开发指南

### 环境要求
- Node.js >= 16
- npm >= 8

### 安装依赖
```bash
npm install
```

### 开发模式
```bash
npm run dev
```
访问: http://localhost:5173

### 构建生产版本
```bash
npm run build
```

### 预览生产版本
```bash
npm run preview
```

## 环境配置

复制 `.env` 文件并根据需要修改配置：

```bash
# API配置
VITE_API_BASE_URL=http://127.0.0.1:8080

# 应用配置
VITE_APP_TITLE=我的相册
```

## 项目结构

```
src/
├── components/          # React组件
│   ├── Login.tsx       # 登录页面
│   ├── Gallery.tsx     # 主画廊页面
│   ├── Header.tsx      # 顶部导航栏
│   ├── Sidebar.tsx     # 左侧边栏
│   ├── PhotoGrid.tsx   # 照片网格
│   └── UploadModal.tsx # 上传模态框
├── services/           # API服务
│   ├── api.ts         # API接口定义
│   └── upload.ts      # 上传服务
├── utils/             # 工具函数
│   └── request.ts     # HTTP请求配置
├── config/            # 配置文件
│   └── constants.ts   # 常量定义
├── App.tsx           # 主应用组件
└── main.tsx          # 应用入口
```

## 部署说明

1. 构建项目: `npm run build`
2. 将 `dist` 目录部署到Web服务器
3. 配置服务器支持SPA路由（如Nginx的try_files）
4. 确保后端API服务正常运行

## 注意事项

- 需要配合后端服务使用
- 默认后端地址为 `http://127.0.0.1:8080`
- 支持的图片格式：常见的图片格式（jpg, png, gif等）
- 单个文件大小限制：50MB
- Token存储在localStorage中，关闭浏览器后需要重新登录（可根据需要修改为sessionStorage）

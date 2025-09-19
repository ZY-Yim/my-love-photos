# My Love Photos - 照片管理系统

基于Spring Boot + 阿里云OSS的轻量级照片管理系统，支持文件夹分类和照片上传下载。

## 功能特性

- 用户登录认证 (JWT)
- 文件夹管理 (创建、删除、树形结构)
- 照片上传下载 (阿里云OSS存储)
- 照片缩略图生成
- 照片分类管理
- 内存存储 (无需数据库)

## 技术栈

- **后端**: Spring Boot 3.5.6 + Spring Security
- **存储**: 阿里云OSS + 内存存储
- **认证**: JWT
- **前端**: React + TypeScript + Vite

## 配置说明

### 1. 用户配置

在 `src/main/resources/application.properties` 中配置用户信息：

```properties
# 用户配置
app.user.username=admin
app.user.password=admin123
app.user.nickname=管理员
```

### 2. 阿里云OSS配置

```properties
aliyun.oss.endpoint=https://oss-cn-hangzhou.aliyuncs.com
aliyun.oss.access-key-id=你的AccessKeyId
aliyun.oss.access-key-secret=你的AccessKeySecret
aliyun.oss.bucket-name=你的Bucket名称
aliyun.oss.base-url=https://你的Bucket名称.oss-cn-hangzhou.aliyuncs.com
```

### 3. JWT配置

```properties
jwt.secret=你的JWT密钥(建议256位以上)
jwt.expiration=86400000
```

## API接口说明

### 认证接口

- `POST /api/login` - 用户登录

### 文件夹接口

- `GET /api/folders` - 获取文件夹列表
- `POST /api/folders` - 创建文件夹
- `DELETE /api/folders/{folderId}` - 删除文件夹

### 照片接口

- `POST /api/upload` - 上传照片
- `GET /api/photos` - 获取照片列表
- `DELETE /api/photos/{photoId}` - 删除照片

## 快速开始

### 1. 准备环境

- JDK 17+
- Maven 3.6+
- 阿里云OSS账号

### 2. 配置项目

1. 修改 `application.properties` 中的配置项
2. 确保阿里云OSS Bucket已创建并配置了公共读权限
3. 设置用户名密码

### 3. 运行项目

```bash
mvn clean package
mvn spring-boot:run
```

或者在IDE中直接运行 `MyLovePhotosApplication` 类。

### 4. 前端运行

```bash
cd front
npm install
npm run dev
```

## 默认用户

系统使用配置文件中的用户信息，默认配置：
- 用户名: `admin`
- 密码: `admin123`

可以通过修改 `application.properties` 来更改用户信息。

## 注意事项

1. 数据存储在内存中，重启应用后数据会丢失
2. 阿里云OSS配置正确，否则文件上传会失败
3. JWT密钥建议使用强随机字符串
4. 生产环境请修改默认用户密码
5. 文件上传大小限制为50MB，可在配置中调整

## 项目特点

- **轻量级**: 无需数据库，使用内存存储
- **快速部署**: 配置简单，启动即用
- **云存储**: 使用阿里云OSS存储文件
- **现代架构**: Spring Boot + React技术栈

## 目录结构

```
src/main/java/com/yanzhiyu/mylovephotos/
├── common/          # 通用类
├── config/          # 配置类
├── controller/      # 控制器层
├── dto/            # 数据传输对象
├── entity/         # 实体类
├── exception/      # 异常处理
├── service/        # 业务逻辑层
└── util/           # 工具类
```

## 扩展功能

可以考虑添加的功能：
- 数据持久化（添加数据库支持）
- 多用户支持
- 照片分享功能
- 照片批量操作
- 照片元数据提取
- 照片搜索功能
- 照片标签系统

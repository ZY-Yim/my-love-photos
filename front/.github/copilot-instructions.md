# Photo Gallery Web Application Instructions

This is a photo gallery management web application with the following features:

## Project Overview
- Login page with username/password authentication
- Main gallery interface with folder management
- Photo upload functionality using Alibaba Cloud OSS direct upload
- Photo viewing and management capabilities

## Key Features
1. **Login Page**: Simple authentication with token storage
2. **Main Gallery**: 
   - Top navigation bar with user info and logout
   - Left sidebar with folder tree navigation
   - Breadcrumb navigation
   - Photo grid display with thumbnails
   - Upload functionality
3. **Upload Process**: 
   - Frontend gets STS credentials from backend
   - Direct upload to OSS using credentials
   - Metadata registration with backend after upload

## Technology Stack
- Modern frontend framework (React/Vue.js)
- Responsive design
- OSS SDK integration
- Local storage for token management

## API Endpoints
- `/api/login` - User authentication
- `/api/sts` - Get temporary OSS credentials
- `/api/photo/metadata` - Register photo metadata

## Development Guidelines
- Use modern ES6+ JavaScript
- Implement responsive design
- Follow component-based architecture
- Handle error states gracefully
- Implement proper loading states

import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import Login from './components/Login';
import Gallery from './components/Gallery';
import { STORAGE_KEYS } from './config/constants';
import './App.css';

// 路由守卫组件
const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const token = localStorage.getItem(STORAGE_KEYS.TOKEN);
  return token ? <>{children}</> : <Navigate to="/login" replace />;
};

function App() {
  return (
    <ConfigProvider locale={zhCN}>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route 
              path="/gallery" 
              element={
                <ProtectedRoute>
                  <Gallery />
                </ProtectedRoute>
              } 
            />
            <Route path="/" element={<Navigate to="/gallery" replace />} />
          </Routes>
        </div>
      </Router>
    </ConfigProvider>
  );
}

export default App;

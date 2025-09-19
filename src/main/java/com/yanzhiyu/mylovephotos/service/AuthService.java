package com.yanzhiyu.mylovephotos.service;

import com.yanzhiyu.mylovephotos.config.UserProperties;
import com.yanzhiyu.mylovephotos.dto.LoginRequest;
import com.yanzhiyu.mylovephotos.dto.LoginResponse;
import com.yanzhiyu.mylovephotos.entity.User;
import com.yanzhiyu.mylovephotos.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    
    @Autowired
    private UserProperties userProperties;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * 用户登录
     */
    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        
        // 验证用户名密码
        if (!userProperties.getUsername().equals(username) || 
            !userProperties.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 生成JWT token
        String token = jwtUtil.generateToken(username);
        
        // 构建用户信息
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            "1", 
            username, 
            userProperties.getNickname()
        );
        
        return new LoginResponse(token, userInfo);
    }
    
    /**
     * 获取当前用户信息
     */
    public User getCurrentUser() {
        return new User(
            userProperties.getUsername(),
            userProperties.getPassword(),
            userProperties.getNickname()
        );
    }
    
    /**
     * 验证用户名
     */
    public boolean validateUser(String username) {
        return userProperties.getUsername().equals(username);
    }
}

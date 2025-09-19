package com.yanzhiyu.mylovephotos.controller;

import com.yanzhiyu.mylovephotos.common.ApiResponse;
import com.yanzhiyu.mylovephotos.dto.LoginRequest;
import com.yanzhiyu.mylovephotos.dto.LoginResponse;
import com.yanzhiyu.mylovephotos.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ApiResponse.success("登录成功", response);
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            return ApiResponse.error(401, e.getMessage());
        }
    }
}

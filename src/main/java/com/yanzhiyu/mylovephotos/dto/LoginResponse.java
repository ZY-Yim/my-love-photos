package com.yanzhiyu.mylovephotos.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private UserInfo userInfo;
    
    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private String id;
        private String username;
        private String nickname;
    }
}

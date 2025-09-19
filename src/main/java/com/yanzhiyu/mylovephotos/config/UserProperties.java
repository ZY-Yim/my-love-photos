package com.yanzhiyu.mylovephotos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.user")
public class UserProperties {
    
    private String username;
    private String password;
    private String nickname;
}

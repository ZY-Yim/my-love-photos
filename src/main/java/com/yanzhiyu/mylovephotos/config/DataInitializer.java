package com.yanzhiyu.mylovephotos.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserProperties userProperties;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("系统初始化完成");
        log.info("默认用户: {}", userProperties.getUsername());
        log.info("系统已启动，可以使用配置的用户名密码登录");
    }
}

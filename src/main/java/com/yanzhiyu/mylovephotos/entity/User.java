package com.yanzhiyu.mylovephotos.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private String username;
    private String password;
    private String nickname;
}

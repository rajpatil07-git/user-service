package com.raj.user.user_service.pojo;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}

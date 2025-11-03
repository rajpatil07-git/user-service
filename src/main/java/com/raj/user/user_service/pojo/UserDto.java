package com.raj.user.user_service.pojo;

import java.util.List;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<String> roles; 
}
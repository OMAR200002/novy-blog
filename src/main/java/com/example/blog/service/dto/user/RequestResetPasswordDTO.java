package com.example.blog.service.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestResetPasswordDTO implements Serializable {
    private String email;
}

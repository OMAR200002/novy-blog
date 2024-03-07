package com.example.blog.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException{

    private int status;
    private String title;
    private String message;
}

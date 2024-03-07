package com.example.blog.service.dto.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyAndPasswordDTO {

    private String key;

    @Size(min = 4, max = 50,message = "password length should be between 4 and 50")
    private String newPassword;


}

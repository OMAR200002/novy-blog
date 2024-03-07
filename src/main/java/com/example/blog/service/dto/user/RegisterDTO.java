package com.example.blog.service.dto.user;

import com.example.blog.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO implements Serializable {
    @NotBlank(message = "username is mandatory")
    @Size(max = 50,message = "username to long (max 50 character)")
    private String username;

    @Size(min = 4, max = 50,message = "password length should be between 4 and 50")
    private String password;

    @Size(max = 50,message = "firstName to long (max 50 character)")
    private String firstName;

    @Size(max = 50,message = "lastName to long (max 50 character)")
    private String lastName;

    @Email(message = "invalid email")
    @NotBlank(message = "email is mandatory")
    private String email;

    @Size(max = 256,message = "imageUrl to long (max 256 character)")
    private String imageUrl;

    public RegisterDTO(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
    }
}

package com.example.blog.api.resource;

import com.example.blog.dao.repository.UserRepository;
import com.example.blog.domain.User;
import com.example.blog.service.MailService;
import com.example.blog.service.UserService;
import com.example.blog.service.dto.user.KeyAndPasswordDTO;
import com.example.blog.service.dto.user.RegisterDTO;
import com.example.blog.service.dto.user.RequestResetPasswordDTO;
import com.example.blog.service.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AccountResource {

    private final MailService mailService;
    private final UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterDTO registerDTO) {
        User user = userService.register(registerDTO, registerDTO.getPassword());
        mailService.sendActivationEmail(user);
    }

    @GetMapping("/activate")
    public String activate(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (user.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),"no.user.was.found.for.this.activation.key","No user was found for this activation key");
        }
        return "activated";
    }

    @PostMapping(path = "/account/reset-password/init")
    public String requestPasswordReset(@RequestBody RequestResetPasswordDTO requestResetPasswordDTO) {
        Optional<User> user = userService.requestPasswordReset(requestResetPasswordDTO.getEmail());
        if (user.isPresent()) {
            mailService.sendPasswordResetMail(user.orElseThrow());
            return user.get().getResetKey();
        } else {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),"password.reset.requested.for.non.existing.mail","No user was found for this activation key");
        }
    }

    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPasswordDTO) {
        if (isPasswordLengthInvalid(keyAndPasswordDTO.getNewPassword())) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),"invalid.password","Invalid password");
        }
        Optional<User> user = userService.completePasswordReset(keyAndPasswordDTO.getNewPassword(), keyAndPasswordDTO.getKey());

        if (user.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),"no.user.was.found.for.this.reset.key","No user was found for this reset key");
        }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() < 4 ||
                        password.length() > 40
        );
    }


}


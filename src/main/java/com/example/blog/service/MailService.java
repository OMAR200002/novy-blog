package com.example.blog.service;



import com.example.blog.domain.User;
import com.example.blog.service.exception.BusinessException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final String BASE_URL = "http://localhost:8080/api";
    private final SpringTemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final String  link = "http://localhost:8080/api/activate?key=";

    public MailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine, MessageSource messageSource) {
        this.javaMailSender = mailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
    }
    @Async
    public void sendEmail(User user){

        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("baseUrl", BASE_URL);
        String content = templateEngine.process("mail/activationEmail", context);


    }

    public void sendActivationEmail(User user) {
        this.sendEmailFromTemplateSync(user, "mail/activationEmail");
    }

    private void sendEmailFromTemplateSync(User user, String templateName){
        if (user.getEmail() == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),"email.doesn't.exist","Email doesn't exist");
        }
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("baseUrl", BASE_URL);
        String content = templateEngine.process(templateName, context);
        this.sendEmailSync(user.getEmail(),content, false, true);
    }
    private void sendEmailSync(String to, String content, boolean isMultipart, boolean isHtml) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom("omarkhadrouni2017@gmail.com");
            message.setSubject("blog account activation is required");
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    @Async
    public void sendPasswordResetMail(User user) {
        this.sendEmailFromTemplateSync(user, "mail/passwordResetEmail");
    }




}


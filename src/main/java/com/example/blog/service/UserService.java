package com.example.blog.service;

import com.example.blog.api.security.AuthoritiesConstants;
import com.example.blog.dao.BlogDao;
import com.example.blog.dao.UserDao;
import com.example.blog.dao.repository.AuthorityRepository;
import com.example.blog.dao.repository.UserRepository;
import com.example.blog.domain.Authority;
import com.example.blog.domain.Blog;
import com.example.blog.domain.User;
import com.example.blog.service.dto.user.RegisterDTO;
import com.example.blog.service.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    private final UserDao userDao;
    private final BlogDao blogDao;

    public User get(Long id){
        return userDao.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(),"user.not.found","User not found"));
    }

    public User register(RegisterDTO registerDTO, String password) {
        userDao
                .findOneByUsername(registerDTO.getUsername().toLowerCase())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new BusinessException(HttpStatus.NOT_FOUND.value(),"username.already.used","Username already used");
                    }
                });
        userDao
                .findOneByEmailIgnoreCase(registerDTO.getEmail())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new BusinessException(HttpStatus.NOT_FOUND.value(),"email.already.used","Email already used");
                    }
                });
        User newUser = new User();
        Blog blog = new Blog();
        blog.setName(registerDTO.getUsername());
        Blog savedBlog = blogDao.save(blog);
        newUser.setBlog(savedBlog);
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setUsername(registerDTO.getUsername().toLowerCase());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());
        if (registerDTO.getEmail() != null) {
            newUser.setEmail(registerDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(registerDTO.getImageUrl());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(generateKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        return newUser;
    }

    public Optional<User> activateRegistration(String key) {
        return userDao
                .findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    return user;
                });
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userDao
                .findOneByEmailIgnoreCase(mail)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(generateKey());
                    user.setResetDate(Instant.now());
                    return user;
                });
    }

    private String generateKey(){
         String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;
        String randomString = new Random().ints(length, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        return randomString;
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        return userDao
                .findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    return user;
                });
    }
}

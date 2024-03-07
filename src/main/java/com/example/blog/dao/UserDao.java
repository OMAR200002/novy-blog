package com.example.blog.dao;

import com.example.blog.dao.repository.UserRepository;
import com.example.blog.domain.User;
import io.micrometer.observation.ObservationFilter;
import lombok.AllArgsConstructor;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDao {
    private final UserRepository userRepository;

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public Optional<User> findOneByUsername(String username){
        return userRepository.findOneByUsername(username);
    }
    public Optional<User> findOneByEmailIgnoreCase(String email){
        return userRepository.findOneByEmailIgnoreCase(email);
    }
    public Optional<User> findOneByActivationKey(String key) {
        return userRepository.findOneByActivationKey(key);
    }
    public Optional<User> findOneByResetKey(String key) {
        return userRepository.findOneByResetKey(key);
    }
}

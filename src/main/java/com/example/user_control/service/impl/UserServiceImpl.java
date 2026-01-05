package com.example.user_control.service.impl;


import com.example.user_control.entity.Role;
import com.example.user_control.entity.User;
import com.example.user_control.repository.UserRepository;
import com.example.user_control.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username already in use");
        }

        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setProfileImage(user.getProfileImage());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(long id) {
        User user = getUserById(id);

        user.setDeletedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}

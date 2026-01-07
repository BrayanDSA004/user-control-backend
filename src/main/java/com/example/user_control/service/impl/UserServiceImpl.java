package com.example.user_control.service.impl;


import com.example.user_control.dto.request.UserCreateRequest;
import com.example.user_control.dto.request.UserUpdateRequest;
import com.example.user_control.dto.response.UserResponse;
import com.example.user_control.entity.Role;
import com.example.user_control.entity.User;
import com.example.user_control.mapper.UserMapper;
import com.example.user_control.repository.UserRepository;
import com.example.user_control.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse createUser(UserCreateRequest request) {

        User user = UserMapper.fromUserCreate(request);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username already in use");
        }

        user.setRole(Role.USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Validar email SOLO si cambió
        if (!existingUser.getEmail().equals(request.email())
                && userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Validar username SOLO si cambió
        if (!existingUser.getUsername().equals(request.username())
                && userRepository.existsByUsernameAndIdNot(request.username(), id)) {
            throw new IllegalArgumentException("Username already in use");
        }

        existingUser.setUsername(request.username());
        existingUser.setEmail(request.email());

        if (request.profileImage() != null) {
            existingUser.setProfileImage(request.profileImage());
        }

        if (request.password() != null && !request.password().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(request.password()));
        }

        userRepository.save(existingUser);
        return UserMapper.toResponse(existingUser);
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserResponse changeRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setRole(role);
        userRepository.save(user);
        return UserMapper.toResponse(user);
    }
}

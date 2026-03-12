package com.example.streakify.service;

import com.example.streakify.dto.UserRequestDto;
import com.example.streakify.dto.UserResponseDto;
import com.example.streakify.exceptionHandling.BadRequestException;
import com.example.streakify.exceptionHandling.ResourceNotFoundException;
import com.example.streakify.model.Users;
import com.example.streakify.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE USER
    public UserResponseDto createUser(UserRequestDto dto) {

        String email = dto.getEmail().toLowerCase().trim();

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email already exists");
        }

        Users user = new Users();
        user.setName(dto.getName().trim());
        user.setEmail(email);

        Users savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    // GET USER
    public UserResponseDto getUserById(Long id) {

        Users user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + id));

        return mapToResponse(user);
    }

    // DELETE USER
    public void deleteUser(Long id) {

        Users user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + id));

        userRepository.delete(user);
    }

    private UserResponseDto mapToResponse(Users user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
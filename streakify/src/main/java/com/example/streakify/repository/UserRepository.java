package com.example.streakify.repository;

import com.example.streakify.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
}

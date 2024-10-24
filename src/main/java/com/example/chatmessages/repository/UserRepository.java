package com.example.chatmessages.repository;

import com.example.chatmessages.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
    List<User> findByUsernameContainingIgnoreCase(String username);
    Optional<User> findByUsername(String username);
}
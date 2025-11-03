package com.raj.user.user_service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.user.user_service.entity.User;

public interface UserDao extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}

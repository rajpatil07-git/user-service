package com.raj.user.user_service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.user.user_service.entity.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);
}

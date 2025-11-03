package com.raj.user.user_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raj.user.user_service.dao.RoleDao;
import com.raj.user.user_service.dao.UserDao;
import com.raj.user.user_service.entity.Role;
import com.raj.user.user_service.entity.User;
import com.raj.user.user_service.pojo.RegisterRequest;
import com.raj.user.user_service.pojo.UserDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public String registerUser(RegisterRequest request) {
        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        // Determine role from request (default: ROLE_USER)
        String roleName = (request.getRole() == null || request.getRole().isEmpty())
                ? "ROLE_USER"
                : request.getRole().toUpperCase();

        // Fetch or create role dynamically
        Role role = roleDao.findByName(roleName)
                .orElseGet(() -> roleDao.save(new Role(null, roleName)));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Create new User entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);

        userDao.save(user);

        return "User registered successfully with role: " + role.getName();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.findAll()
                .stream()
                .map(user -> {
                    UserDto dto = modelMapper.map(user, UserDto.class);
                    dto.setRoles(user.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        UserDto dto = modelMapper.map(user, UserDto.class);
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    // Spring Security integration for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String[] roles = user.getRoles().stream()
                .map(role -> role.getName().replace("ROLE_", "")) // Spring auto-adds ROLE_ prefix
                .toArray(String[]::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}

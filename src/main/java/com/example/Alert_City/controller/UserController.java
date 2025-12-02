package com.example.Alert_City.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Alert_City.dto.UserDTO;
import com.example.Alert_City.enums.ProfileType;
import com.example.Alert_City.exceptions.ResourceNotFoundException;
import com.example.Alert_City.mapper.UserMapper;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean isAuthorized(Long requestedUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        String email = authentication.getName();
        Optional<UserModel> authenticatedUserOpt = userService.findByEmail(email);
        if (authenticatedUserOpt.isEmpty()) {
            return false;
        }
        UserModel authenticatedUser = authenticatedUserOpt.get();
        return authenticatedUser.getId().equals(requestedUserId) || authenticatedUser.getProfileType() == ProfileType.ADMIN;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        if (!isAuthorized(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        UserModel user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        UserDTO userDTO = UserMapper.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, String> request) {
        if (!isAuthorized(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        UserModel user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (request.containsKey("name")) {
            user.setName(request.get("name"));
        }
        if (request.containsKey("password")) {
            user.setPassword(passwordEncoder.encode(request.get("password")));
        }
        userService.updateUser(user);
        UserDTO userDTO = UserMapper.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }
}

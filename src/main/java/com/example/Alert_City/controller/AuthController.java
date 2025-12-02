package com.example.Alert_City.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Alert_City.dto.LoginResponseDTO;
import com.example.Alert_City.dto.UserDTO;
import com.example.Alert_City.enums.ProfileType;
import com.example.Alert_City.mapper.UserMapper;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.security.JwtUtil;
import com.example.Alert_City.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password");
        String profileTypeStr = request.getOrDefault("profileType", "CITIZEN");
        String institutionIdStr = request.get("institutionId");
        ProfileType profileType = ProfileType.valueOf(profileTypeStr.toUpperCase());
        Long institutionId = institutionIdStr != null && !institutionIdStr.isEmpty() ? Long.parseLong(institutionIdStr) : null;
        UserModel user = userService.registerUser(name, email, password, profileType, institutionId);
        UserDTO userDTO = UserMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        Optional<UserModel> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = JwtUtil.generateToken(user.getEmail());
                UserDTO userDTO = UserMapper.toDTO(user);
                LoginResponseDTO response = new LoginResponseDTO(token, userDTO);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }
}
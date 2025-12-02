package com.example.Alert_City.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Alert_City.enums.ProfileType;
import com.example.Alert_City.model.InstitutionModel;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.repository.InstitutionRepository;
import com.example.Alert_City.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel registerUser(String name, String email, String password, ProfileType profileType, Long institutionId) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setProfileType(profileType);
        if (institutionId != null) {
            Optional<InstitutionModel> institutionOpt = institutionRepository.findById(institutionId);
            institutionOpt.ifPresent(user::setInstitution);
        }
        return userRepository.save(user);
    }

    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserModel> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel updateUser(UserModel user) {
        return userRepository.save(user);
    }
}

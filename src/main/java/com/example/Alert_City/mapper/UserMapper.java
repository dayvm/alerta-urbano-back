package com.example.Alert_City.mapper;

import com.example.Alert_City.dto.UserDTO;
import com.example.Alert_City.model.UserModel;

public class UserMapper {

    public static UserDTO toDTO(UserModel user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfileType(user.getProfileType());
        if (user.getInstitution() != null) {
            dto.setInstitutionId(user.getInstitution().getId());
            dto.setInstitutionName(user.getInstitution().getName());
        }
        return dto;
    }
}

package com.example.Alert_City.mapper;

import com.example.Alert_City.dto.InstitutionDTO;
import com.example.Alert_City.model.InstitutionModel;

public class InstitutionMapper {

    public static InstitutionDTO toDTO(InstitutionModel institution) {
        if (institution == null) {
            return null;
        }
        InstitutionDTO dto = new InstitutionDTO();
        dto.setId(institution.getId());
        dto.setName(institution.getName());
        dto.setDescription(institution.getDescription());
        dto.setLogoUrl(institution.getLogoUrl());
        dto.setOfficialContact(institution.getOfficialContact());
        return dto;
    }
}

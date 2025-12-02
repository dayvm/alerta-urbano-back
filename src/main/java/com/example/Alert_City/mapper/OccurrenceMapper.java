package com.example.Alert_City.mapper;

import com.example.Alert_City.dto.OccurrenceDTO;
import com.example.Alert_City.model.OccurrenceModel;

public class OccurrenceMapper {

    public static OccurrenceDTO toDTO(OccurrenceModel occurrence) {
        if (occurrence == null) {
            return null;
        }
        OccurrenceDTO dto = new OccurrenceDTO();
        dto.setId(occurrence.getId());
        dto.setTitle(occurrence.getTitle());
        dto.setDescription(occurrence.getDescription());
        dto.setLatitude(occurrence.getLatitude());
        dto.setLongitude(occurrence.getLongitude());
        dto.setAddressText(occurrence.getAddressText());
        dto.setCurrentStatus(occurrence.getCurrentStatus());
        dto.setCreatedAt(occurrence.getCreatedAt());
        if (occurrence.getAuthor() != null) {
            dto.setAuthorId(occurrence.getAuthor().getId());
            dto.setAuthorName(occurrence.getAuthor().getName());
        }
        if (occurrence.getCategory() != null) {
            dto.setCategoryId(occurrence.getCategory().getId());
            dto.setCategoryName(occurrence.getCategory().getName());
        }
        if (occurrence.getResponsibleInstitution() != null) {
            dto.setResponsibleInstitutionId(occurrence.getResponsibleInstitution().getId());
            dto.setResponsibleInstitutionName(occurrence.getResponsibleInstitution().getName());
        }
        return dto;
    }
}

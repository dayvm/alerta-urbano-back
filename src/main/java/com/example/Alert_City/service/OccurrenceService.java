package com.example.Alert_City.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Alert_City.enums.OccurrenceStatus;
import com.example.Alert_City.model.CategoryModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.model.InstitutionModel;
import com.example.Alert_City.repository.OccurrenceRepository;


@Service
public class OccurrenceService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;

    public List<OccurrenceModel> findAll() {
        return occurrenceRepository.findAll();
    }

    public List<OccurrenceModel> findByAuthorId(Long authorId) {
        return occurrenceRepository.findByAuthorId(authorId);
    }

    public List<OccurrenceModel> findByInstitutionId(Long institutionId) {
        return occurrenceRepository.findByResponsibleInstitutionId(institutionId);
    }

    public Optional<OccurrenceModel> findById(Long id) {
        return occurrenceRepository.findById(id);
    }

    public OccurrenceModel createOccurrence(String title, String description, Double latitude, Double longitude, String addressText, UserModel author, CategoryModel category, InstitutionModel responsibleInstitution) {
        OccurrenceModel occurrence = new OccurrenceModel();
        occurrence.setTitle(title);
        occurrence.setDescription(description);
        occurrence.setLatitude(latitude);
        occurrence.setLongitude(longitude);
        occurrence.setAddressText(addressText);
        occurrence.setCurrentStatus(OccurrenceStatus.OPEN);
        occurrence.setCreatedAt(LocalDateTime.now());
        occurrence.setAuthor(author);
        occurrence.setCategory(category);
        occurrence.setResponsibleInstitution(responsibleInstitution);
        return occurrenceRepository.save(occurrence);
    }

    public OccurrenceModel updateStatus(OccurrenceModel occurrence, OccurrenceStatus newStatus) {
        occurrence.setCurrentStatus(newStatus);
        return occurrenceRepository.save(occurrence);
    }

    public void deleteOccurrence(OccurrenceModel occurrence) {
        occurrenceRepository.delete(occurrence);
    }
}

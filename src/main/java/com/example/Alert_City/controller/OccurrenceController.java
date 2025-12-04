package com.example.Alert_City.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Alert_City.dto.OccurrenceDTO;
import com.example.Alert_City.enums.OccurrenceStatus;
import com.example.Alert_City.exceptions.ResourceNotFoundException;
import com.example.Alert_City.mapper.OccurrenceMapper;
import com.example.Alert_City.model.CategoryModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.model.InstitutionModel;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.service.CategoryService;
import com.example.Alert_City.service.OccurrenceService;
import com.example.Alert_City.service.UserService;
import com.example.Alert_City.service.InstitutionService;

@RestController
@RequestMapping("/occurrences")
public class OccurrenceController {

    @Autowired
    private OccurrenceService occurrenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InstitutionService institutionService;

    @GetMapping
    public ResponseEntity<?> getAllOccurrences(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) Long institutionId
    ) {
        List<OccurrenceModel> occurrences;

        if (userId != null) {
            occurrences = occurrenceService.findByAuthorId(userId);
    } else if (institutionId != null) {
        occurrences = occurrenceService.findByInstitutionId(institutionId);
    } else {
        occurrences = occurrenceService.findAll();
    }

    List<OccurrenceDTO> response = occurrences.stream()
            .map(OccurrenceMapper::toDTO)
            .collect(Collectors.toList());

    return ResponseEntity.ok(response);
}

    @GetMapping("/{id}")
    public ResponseEntity<?> getOccurrenceById(@PathVariable Long id) {
        OccurrenceModel occurrence = occurrenceService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with id: " + id));
        OccurrenceDTO dto = OccurrenceMapper.toDTO(occurrence);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> createOccurrence(@RequestBody Map<String, Object> request) {
        String title = (String) request.get("title");
        String description = (String) request.get("description");
        Double latitude = Double.valueOf(request.get("latitude").toString());
        Double longitude = Double.valueOf(request.get("longitude").toString());
        String addressText = (String) request.get("addressText");
        Long authorId = Long.valueOf(request.get("authorId").toString());
        Long categoryId = Long.valueOf(request.get("categoryId").toString());
        InstitutionModel responsibleInstitution = null;
        if (request.get("responsibleInstitutionId") != null) {
            Long institutionId = Long.valueOf(request.get("responsibleInstitutionId").toString());
            // Busca a instituição, se não achar, lança erro (ou deixa null se preferir ser leniente)
            responsibleInstitution = institutionService.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found with id: " + institutionId));
        }
        UserModel user = userService.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + authorId));
        CategoryModel category = categoryService.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        OccurrenceModel occurrence = occurrenceService.createOccurrence(title, description, latitude, longitude, addressText, user, category, responsibleInstitution);
        OccurrenceDTO dto = OccurrenceMapper.toDTO(occurrence);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        OccurrenceModel occurrence = occurrenceService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with id: " + id));
        String statusStr = request.get("currentStatus");
        OccurrenceStatus newStatus = OccurrenceStatus.valueOf(statusStr.toUpperCase());
        OccurrenceModel updated = occurrenceService.updateStatus(occurrence, newStatus);
        OccurrenceDTO dto = OccurrenceMapper.toDTO(updated);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOccurrence(@PathVariable Long id) {
        OccurrenceModel occurrence = occurrenceService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with id: " + id));
        occurrenceService.deleteOccurrence(occurrence);
        return ResponseEntity.noContent().build();
    }
}

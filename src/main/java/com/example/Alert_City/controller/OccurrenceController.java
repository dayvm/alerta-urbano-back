package com.example.Alert_City.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.Alert_City.mapper.OccurrenceMapper;
import com.example.Alert_City.model.CategoryModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.service.CategoryService;
import com.example.Alert_City.service.OccurrenceService;
import com.example.Alert_City.service.UserService;

@RestController
@RequestMapping("/occurrences")
public class OccurrenceController {

    @Autowired
    private OccurrenceService occurrenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllOccurrences(@RequestParam(required = false) Long userId) {
        List<OccurrenceModel> occurrences;
        if (userId != null) {
            occurrences = occurrenceService.findByAuthorId(userId);
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
        Optional<OccurrenceModel> occOpt = occurrenceService.findById(id);
        if (occOpt.isPresent()) {
            OccurrenceDTO dto = OccurrenceMapper.toDTO(occOpt.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Occurrence not found");
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
        Optional<UserModel> userOpt = userService.findById(authorId);
        Optional<CategoryModel> categoryOpt = categoryService.findById(categoryId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid author ID");
        }
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category ID");
        }
        OccurrenceModel occurrence = occurrenceService.createOccurrence(title, description, latitude, longitude, addressText, userOpt.get(), categoryOpt.get());
        OccurrenceDTO dto = OccurrenceMapper.toDTO(occurrence);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<OccurrenceModel> occOpt = occurrenceService.findById(id);
        if (occOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Occurrence not found");
        }
        String statusStr = request.get("currentStatus");
        OccurrenceStatus newStatus = OccurrenceStatus.valueOf(statusStr.toUpperCase());
        OccurrenceModel updated = occurrenceService.updateStatus(occOpt.get(), newStatus);
        OccurrenceDTO dto = OccurrenceMapper.toDTO(updated);
        return ResponseEntity.ok(dto);
    }
}

package com.example.Alert_City.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Alert_City.dto.InstitutionDTO;
import com.example.Alert_City.mapper.InstitutionMapper;
import com.example.Alert_City.model.InstitutionModel;
import com.example.Alert_City.service.InstitutionService;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @GetMapping
    public ResponseEntity<?> getAllInstitutions() {
        List<InstitutionModel> institutions = institutionService.findAll();
        List<InstitutionDTO> response = institutions.stream().map(InstitutionMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}

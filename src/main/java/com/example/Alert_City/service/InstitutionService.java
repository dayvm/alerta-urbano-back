package com.example.Alert_City.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Alert_City.model.InstitutionModel;
import com.example.Alert_City.repository.InstitutionRepository;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    public List<InstitutionModel> findAll() {
        return institutionRepository.findAll();
    }
    public Optional<InstitutionModel> findById(Long id) {
        return institutionRepository.findById(id);
    }
}

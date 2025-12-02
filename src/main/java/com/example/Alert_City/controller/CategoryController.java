package com.example.Alert_City.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Alert_City.dto.CategoryDTO;
import com.example.Alert_City.mapper.CategoryMapper;
import com.example.Alert_City.model.CategoryModel;
import com.example.Alert_City.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<CategoryModel> categories = categoryService.findAll();
        List<CategoryDTO> response = categories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}

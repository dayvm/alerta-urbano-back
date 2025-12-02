package com.example.Alert_City.mapper;

import com.example.Alert_City.dto.CategoryDTO;
import com.example.Alert_City.model.CategoryModel;

public class CategoryMapper {

    public static CategoryDTO toDTO(CategoryModel category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlaHours(category.getSlaHours());
        dto.setDefaultPriority(category.getDefaultPriority());
        return dto;
    }
}

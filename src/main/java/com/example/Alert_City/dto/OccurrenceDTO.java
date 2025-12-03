package com.example.Alert_City.dto;

import java.time.LocalDateTime;

import com.example.Alert_City.enums.OccurrenceStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccurrenceDTO {
    private Long id;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private String addressText;
    private OccurrenceStatus currentStatus;
    private LocalDateTime createdAt;
    private Long authorId;
    private String authorName;
    private Long categoryId;
    private String categoryName;
    private Long responsibleInstitutionId;
    private String responsibleInstitutionName;
    private String photoUrl;
}

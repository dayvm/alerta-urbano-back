package com.example.Alert_City.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Alert_City.model.OccurrenceModel;

@Repository
public interface OccurrenceRepository extends JpaRepository<OccurrenceModel, Long> {
    List<OccurrenceModel> findByAuthorId(Long authorId);

    List<OccurrenceModel> findByResponsibleInstitutionId(Long institutionId);
}

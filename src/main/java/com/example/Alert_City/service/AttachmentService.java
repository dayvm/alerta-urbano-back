package com.example.Alert_City.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Alert_City.model.AttachmentModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.repository.AttachmentRepository;
import com.example.Alert_City.repository.OccurrenceRepository; // <--- Importe isso

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private OccurrenceRepository occurrenceRepository; // <--- Injete o repositório da ocorrência

    public AttachmentModel saveAttachment(String fileUrl, String fileType, OccurrenceModel occurrence) {
        AttachmentModel attachment = new AttachmentModel();
        attachment.setFileUrl(fileUrl);
        attachment.setFileType(fileType);
        attachment.setOccurrence(occurrence);
        if (occurrence.getPhotoUrl() == null || occurrence.getPhotoUrl().isEmpty()) {
            occurrence.setPhotoUrl(fileUrl);
            occurrenceRepository.save(occurrence); // Salva a alteração na ocorrência
        }
        return attachmentRepository.save(attachment);
    }
}

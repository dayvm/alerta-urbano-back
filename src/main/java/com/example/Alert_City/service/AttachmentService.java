package com.example.Alert_City.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Alert_City.model.AttachmentModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.repository.AttachmentRepository;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    public AttachmentModel saveAttachment(String fileUrl, String fileType, OccurrenceModel occurrence) {
        AttachmentModel attachment = new AttachmentModel();
        attachment.setFileUrl(fileUrl);
        attachment.setFileType(fileType);
        attachment.setOccurrence(occurrence);
        return attachmentRepository.save(attachment);
    }
}

package com.example.Alert_City.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Alert_City.dto.AttachmentUploadResponseDTO;
import com.example.Alert_City.exceptions.ResourceNotFoundException;
import com.example.Alert_City.model.AttachmentModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.service.AttachmentService;
import com.example.Alert_City.service.OccurrenceService;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private OccurrenceService occurrenceService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(required = false) Long occurrenceId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath);
            String fileUrl = "/" + UPLOAD_DIR + uniqueFilename;
            String fileType = file.getContentType();
            AttachmentModel attachment = null;
            if (occurrenceId != null) {
                OccurrenceModel occurrence = occurrenceService.findById(occurrenceId).orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with id: " + occurrenceId));
                attachment = attachmentService.saveAttachment(fileUrl, fileType, occurrence);
            }
            AttachmentUploadResponseDTO response = new AttachmentUploadResponseDTO();
            response.setUrl(fileUrl);
            if (attachment != null) {
                response.setId(attachment.getId());
            }
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
}

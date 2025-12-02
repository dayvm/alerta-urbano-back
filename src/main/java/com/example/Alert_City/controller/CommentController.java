package com.example.Alert_City.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Alert_City.dto.CommentDTO;
import com.example.Alert_City.exceptions.ResourceNotFoundException;
import com.example.Alert_City.mapper.CommentMapper;
import com.example.Alert_City.model.CommentModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.service.CommentService;
import com.example.Alert_City.service.OccurrenceService;
import com.example.Alert_City.service.UserService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private OccurrenceService occurrenceService;

    @GetMapping
    public ResponseEntity<?> getCommentsByOccurrence(@RequestParam Long occurrenceId) {
        List<CommentModel> comments = commentService.findByOccurrenceId(occurrenceId);
        List<CommentDTO> response = comments.stream().map(CommentMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Map<String, Object> request) {
        String text = (String) request.get("text");
        Long occurrenceId = Long.valueOf(request.get("occurrenceId").toString());
        Long userId = Long.valueOf(request.get("userId").toString());
        UserModel user = userService.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        OccurrenceModel occurrence = occurrenceService.findById(occurrenceId).orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with id: " + occurrenceId));
        CommentModel comment = commentService.createComment(text, user, occurrence);
        CommentDTO commentDTO = CommentMapper.toDTO(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }
}

package com.example.Alert_City.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Alert_City.model.CommentModel;
import com.example.Alert_City.model.OccurrenceModel;
import com.example.Alert_City.model.UserModel;
import com.example.Alert_City.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentModel> findByOccurrenceId(Long occurrenceId) {
        return commentRepository.findByOccurrenceId(occurrenceId);
    }

    public CommentModel createComment(String text, UserModel user, OccurrenceModel occurrence) {
        CommentModel comment = new CommentModel();
        comment.setText(text);
        comment.setPostedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setOccurrence(occurrence);
        return commentRepository.save(comment);
    }
}

package com.example.Alert_City.mapper;

import com.example.Alert_City.dto.CommentDTO;
import com.example.Alert_City.model.CommentModel;

public class CommentMapper {

    public static CommentDTO toDTO(CommentModel comment) {
        if (comment == null) {
            return null;
        }
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setPostedAt(comment.getPostedAt());
        if (comment.getUser() != null) {
            dto.setUserId(comment.getUser().getId());
            dto.setUserName(comment.getUser().getName());
            dto.setUserProfileType(comment.getUser().getProfileType());
        }
        if (comment.getOccurrence() != null) {
            dto.setOccurrenceId(comment.getOccurrence().getId());
        }
        return dto;
    }
}

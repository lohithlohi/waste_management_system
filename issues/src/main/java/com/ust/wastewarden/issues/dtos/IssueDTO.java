package com.ust.wastewarden.issues.dtos;

import com.ust.wastewarden.issues.model.IssueStatus;

import java.time.LocalDateTime;

public record IssueDTO(
        Long id,
        String description,
        UserDTO sender,
        BinDTO bin,
        IssueStatus status,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) { }

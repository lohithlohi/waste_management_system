package com.ust.wastewarden.notifications.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDTO(
        Long id,
        String username,
        String email,
        String location
) {
}

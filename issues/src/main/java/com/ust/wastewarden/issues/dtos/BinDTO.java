package com.ust.wastewarden.issues.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public record BinDTO(
        Long id,
        String location,
        Double latitude,
        Double longitude,
        int fillLevel
) { }

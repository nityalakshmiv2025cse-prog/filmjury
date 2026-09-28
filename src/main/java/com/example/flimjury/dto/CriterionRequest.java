package com.example.flimjury.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterionRequest {
    @NotBlank(message = "Name is required") private String name;
    @Positive(message = "Max score must be positive") private int maxScore;
}

package com.example.flimjury.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterionScore {
    @NotNull(message = "Criterion id is required") private Long criterionId;
    @NotNull(message = "Score is required") @Positive(message = "Score must be positive") private Integer score;
}

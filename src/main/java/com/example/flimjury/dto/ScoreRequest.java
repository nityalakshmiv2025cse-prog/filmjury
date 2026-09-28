package com.example.flimjury.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreRequest {
    @NotNull(message = "Judge id is required") private Long judgeId;
    @Valid @NotEmpty(message = "Scores are required") private List<CriterionScore> scores;
}

package com.example.flimjury.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRequest {
    @NotEmpty(message = "At least one entry id is required") private List<Long> entryIds;
}

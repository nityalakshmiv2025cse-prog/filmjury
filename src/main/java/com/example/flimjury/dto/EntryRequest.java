package com.example.flimjury.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryRequest {
    @NotBlank(message = "Title is required") private String title;
    @NotBlank(message = "Genre is required") private String genre;
    @NotBlank(message = "Video link is required") private String videoLink;
    @NotBlank(message = "Team name is required") private String teamName;
}

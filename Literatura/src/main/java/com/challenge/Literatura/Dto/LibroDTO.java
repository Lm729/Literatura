package com.challenge.Literatura.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("title")
        String title,
        @JsonAlias("authors")
        List<AutorDTO> authors,
        @JsonAlias("languages")
        List<String> languages,
        @JsonAlias("download_count")
        long downloads
) {

}

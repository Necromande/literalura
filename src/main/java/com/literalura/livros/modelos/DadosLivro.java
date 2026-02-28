package com.literalura.livros.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        int id,
        String title,
        @JsonProperty("authors") List<Autor> autores,
        @JsonProperty("languages") List<String> idiomas,
        @JsonProperty("download_count") int downloads
) {}




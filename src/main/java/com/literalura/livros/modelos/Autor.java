package com.literalura.livros.modelos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(
        String name,
        Integer birth_year,
        Integer death_year
) {}


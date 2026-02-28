package com.literalura.livros.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ConverteDados {

    private final ObjectMapper mapper = new ObjectMapper();
    public <T> List<T> obterLista(String json, Class<T> classe) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonNode resultsNode = root.path("results");

            if (resultsNode == null || !resultsNode.isArray()) {
                return Collections.emptyList();
            }

            return mapper.readerForListOf(classe).readValue(resultsNode);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }



}


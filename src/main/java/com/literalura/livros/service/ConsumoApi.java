package com.literalura.livros.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ConsumoApi {

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public String obterDados(String endereco) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))

                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Body (primeiros 500 chars): " + response.body().substring(0, Math.min(500, response.body().length())));



            return response.body();



        } catch (Exception e) {
            throw new RuntimeException("Erro ao consumir API: " + e.getMessage(), e);
        }
    }

}


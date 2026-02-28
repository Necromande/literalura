package com.literalura.livros.modelos;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "livros")
public class LivroEntity {

    @Id
    private int id;
    private String title;
    private String idioma;
    private int downloadCount;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> autores;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> deathYears;


    public LivroEntity() {}

    public LivroEntity(DadosLivro dados) {
        this.id = dados.id();
        this.title = dados.title();
        this.idioma = dados.idiomas().isEmpty() ? "desconhecido" : dados.idiomas().get(0);
        this.downloadCount = dados.downloads();

        // agora pega o nome direto do Autor
        this.autores = dados.autores().stream()
                .map(Autor::name)
                .toList();
        this.deathYears = dados.autores().stream()
                .map(Autor::death_year)
                .toList();

    }


    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getIdioma() { return idioma; }
    public int getDownloadCount() { return downloadCount; }
    public List<String> getAutores() { return autores; }

    public List<Integer> getDeathYears() {
        return deathYears;
    }

    public void setDeathYears(List<Integer> deathYears) {
        this.deathYears = deathYears;
    }
}
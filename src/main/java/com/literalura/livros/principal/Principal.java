package com.literalura.livros.principal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.literalura.livros.modelos.DadosLivro;
import com.literalura.livros.modelos.LivroEntity;
import com.literalura.livros.repository.LivroRepository;
import com.literalura.livros.service.ConsumoApi;
import com.literalura.livros.service.ConverteDados;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final LivroRepository livroRepository;

    public Principal(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                Escolha o número de sua opção:
                1 - buscar livro pelo título
                2 - listar livros registrados
                3 - listar autores registrados
                4 - listar autores vivos em um determinado ano
                5 - listar livros em um determinado idioma
                0 - sair
                """);

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Encerrando programa...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o título do livro:");
        String titulo = scanner.nextLine();
        String tituloEncoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8).replace("+","%20");

        System.out.println(tituloEncoded);

        String json = consumoApi.obterDados("https://gutendex.com/books?search=" + tituloEncoded);

        List<DadosLivro> livros = conversor.obterLista(json, DadosLivro.class);
        System.out.println(livros);

        if (livros == null || livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o título informado.");
            return;
        }



        livros.forEach(dados -> {
            System.out.println("Título: " + dados.title());

            // autores agora é List<Autor>
            dados.autores().forEach(autor -> {
                System.out.println("Autor: " + autor.name() +
                        " (" + autor.birth_year() + " - " + autor.death_year() + ")");
            });

            // idiomas e downloads em português
            System.out.println("Idiomas: " + dados.idiomas());
            System.out.println("Downloads: " + dados.downloads());
            System.out.println("-----------");

            LivroEntity entidade = new LivroEntity(dados);
            livroRepository.save(entidade);
        });
    }

    private void listarLivros() {
        livroRepository.findAll().forEach(livro ->
                System.out.println("Título: " + livro.getTitle()));
    }

    private void listarAutores() {
        livroRepository.findAll().forEach(livro ->
                livro.getAutores().forEach(autor -> System.out.println("Autor: " + autor)));
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite o ano:");
        int ano = scanner.nextInt();
        scanner.nextLine();

        livroRepository.findAll().forEach(livro -> {
            List<String> nomes = livro.getAutores();
            List<Integer> mortes = livro.getDeathYears();

            if (nomes != null && !nomes.isEmpty()) {
                for (int i = 0; i < nomes.size(); i++) {
                    String nome = nomes.get(i);
                    Integer deathYear = (mortes != null && mortes.size() > i) ? mortes.get(i) : null;

                    if (deathYear == null) {
                        System.out.println("Não há dados completos para o autor: " + nome);
                    } else if (deathYear >= ano) {
                        System.out.println("Autor vivo em " + ano + ": " + nome);
                    } else {
                        System.out.println("Autor NÃO estava vivo em " + ano + ": " + nome);
                    }
                }
            } else {
                System.out.println("Livro '" + livro.getTitle() + "' não possui autores registrados.");
            }
        });
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite o idioma (ex: 'pt'):");
        String idioma = scanner.nextLine();

        livroRepository.findAll().stream()
                .filter(livro -> livro.getIdioma().equalsIgnoreCase(idioma))
                .forEach(livro -> System.out.println("Livro em " + idioma + ": " + livro.getTitle()));
    }
}
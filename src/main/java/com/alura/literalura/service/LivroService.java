package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAllComAutores();
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosEmAno(int ano) {
        return autorRepository.findAutoresVivosEmAno(ano);
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    @Transactional
    public Livro buscarLivroPorTitulo(String titulo) {
        Optional<Livro> livroExistente = livroRepository.findByTituloIgnoreCase(titulo);
        if (livroExistente.isPresent()) {
            System.out.println("Livro já está no banco de dados.");
            return livroExistente.get();
        }

        System.out.println("Buscando na API Gutendex...");
        String json = consumirApiGutendex(titulo);
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.path("results");
            if (results.isArray() && !results.isEmpty()) {
                JsonNode bookNode = results.get(0);
                String bookTitle = bookNode.path("title").asText();
                String idioma = bookNode.path("languages").get(0).asText();
                int downloadCount = bookNode.path("download_count").asInt();

                Livro novoLivro = new Livro(bookTitle, idioma, downloadCount);

                List<Autor> autores = new ArrayList<>();
                for (JsonNode authorNode : bookNode.path("authors")) {
                    String nomeAutor = authorNode.path("name").asText();
                    Integer anoNascimento = authorNode.path("birth_year").asInt();
                    Integer anoFalecimento = authorNode.path("death_year").asInt();

                    Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(nomeAutor);
                    if (autorExistente.isPresent()) {
                        autores.add(autorExistente.get());
                    } else {
                        Autor novoAutor = new Autor(nomeAutor, anoNascimento, anoFalecimento);
                        autores.add(novoAutor);
                    }
                }

                novoLivro.setAutores(autores);
                livroRepository.save(novoLivro);
                System.out.println("Livro salvo no banco de dados.");
                return novoLivro;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String consumirApiGutendex(String titulo) {
        try {
            String encodedTitulo = java.net.URLEncoder.encode(titulo, "UTF-8");
            String url = "https://gutendex.com/books/?search=" + encodedTitulo;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

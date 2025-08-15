package com.alura.literalura.cli;

import com.alura.literalura.model.Livro;
import com.alura.literalura.service.LivroService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Menu {
    private final LivroService livroService;

    public Menu(LivroService livroService) {
        this.livroService = livroService;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=== MENU LITERALURA ===");
            System.out.println("1 - Buscar livro por título");
            System.out.println("2 - Listar livros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos em um determinado ano");
            System.out.println("5 - Listar livros em um determinado idioma");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Digite o título do livro: ");
                        String titulo = scanner.nextLine();
                        var livro = livroService.buscarLivroPorTitulo(titulo);
                        if (livro != null) {
                            System.out.println("--- LIVRO ---");
                            System.out.println("Título: " + livro.getTitulo());
                            System.out.println("Autor(es): " + livro.getAutores().stream()
                                    .map(a -> a.getNome())
                                    .collect(Collectors.joining(", ")));
                            System.out.println("Idioma: " + livro.getIdioma());
                            System.out.println("Downloads: " + livro.getDownloadCount());
                            System.out.println("-------------");
                        } else System.out.println("Livro não encontrado.");
                    }
                    case 2 -> {
                        List<com.alura.literalura.model.Livro> todosLivros = livroService.listarLivros();
                        if (todosLivros.isEmpty()) {
                            System.out.println("Nenhum livro registrado.");
                        } else {
                            todosLivros.forEach(l -> {
                                System.out.println("--- LIVRO ---");
                                System.out.println("Título: " + l.getTitulo());
                                System.out.println("Autor(es): " + l.getAutores().stream()
                                        .map(a -> a.getNome())
                                        .collect(Collectors.joining(", ")));
                                System.out.println("Idioma: " + l.getIdioma());
                                System.out.println("Downloads: " + l.getDownloadCount());
                                System.out.println("-------------");
                            });
                        }
                    }
                    case 3 -> {
                        livroService.listarAutores().forEach(autor -> {
                            System.out.println("--- AUTOR ---");
                            System.out.println("Nome: " + autor.getNome());
                            System.out.println("Ano de Nascimento: " + autor.getAnoNascimento());
                            System.out.println("Ano de Falecimento: " + autor.getAnoFalecimento());
                            System.out.println("Livros: " + autor.getLivros().stream()
                                    .map(Livro::getTitulo)
                                    .collect(Collectors.joining(", ")));
                            System.out.println("-------------");
                        });
                    }
                    case 4 -> {
                        System.out.print("Digite o ano: ");
                        int ano = scanner.nextInt();
                        scanner.nextLine();
                        livroService.listarAutoresVivosEmAno(ano).forEach(System.out::println);
                    }
                    case 5 -> {
                        System.out.println("Selecione o idioma:");
                        System.out.println("1 - Português (pt)");
                        System.out.println("2 - Francês (fr)");
                        System.out.println("3 - Inglês (en)");
                        System.out.println("4 - Espanhol (es)");
                        System.out.print("Escolha uma opção: ");
                        int opcaoIdioma = scanner.nextInt();
                        scanner.nextLine();
                        String idioma = "";
                        switch (opcaoIdioma) {
                            case 1 -> idioma = "pt";
                            case 2 -> idioma = "fr";
                            case 3 -> idioma = "en";
                            case 4 -> idioma = "es";
                            default -> {
                                System.out.println("Opção de idioma inválida!");
                                continue;
                            }
                        }

                        livroService.listarLivrosPorIdioma(idioma)
                                .forEach(l -> {
                                    System.out.println("--- LIVRO ---");
                                    System.out.println("Título: " + l.getTitulo());
                                    System.out.println("Autor(es): " + l.getAutores().stream()
                                            .map(a -> a.getNome())
                                            .collect(Collectors.joining(", ")));
                                    System.out.println("Idioma: " + l.getIdioma());
                                    System.out.println("Downloads: " + l.getDownloadCount());
                                    System.out.println("-------------");
                                });
                    }
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpa o buffer do scanner
                opcao = -1; // Garante que o loop continue
            }
        } while (opcao != 0);
        scanner.close();
    }
}

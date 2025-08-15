package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloIgnoreCase(String titulo);
    List<Livro> findByIdioma(String idioma);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores")
    List<Livro> findAllComAutores();
}

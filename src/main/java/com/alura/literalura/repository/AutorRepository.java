package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoFalecimento >= :ano OR a.anoFalecimento IS NULL")
    List<Autor> findAutoresVivosEmAno(int ano);
}
